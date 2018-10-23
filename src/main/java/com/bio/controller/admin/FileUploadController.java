package com.bio.controller.admin;

import com.bio.Utils.ClientInfoUtils;
import com.bio.Utils.DBUtils;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Center;
import com.bio.beans.Exam;
import com.bio.beans.Person;
import com.bio.service.ICenterService;
import com.bio.service.IExamService;
import com.bio.service.IPersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"username", "user", "wxuser"})// 此处定义此Controller中将要创建和使用哪些session中的对象名
public class FileUploadController {
    @Autowired
    private IPersonService personService;
    @Autowired
    private ICenterService centerService;
    @Autowired
    private IExamService examService;

    private static Logger logger = Logger.getLogger(FileUploadController.class);
    private static String UNDERLINE = "_";

    // 上传多个文件
    @RequestMapping(value = "/uploadMultiFiles")
    public ModelAndView uploadMultiFiles(@ModelAttribute("username") String username) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", username);
        mv.setViewName("jsp/upload/uploadFiles");
        return mv;
    }

    @RequestMapping(value = "/upMultiFiles", method = RequestMethod.POST)
    public String upMultiFiles(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam("files") MultipartFile[] files, ModelMap session,
                               Model model) {
        List<MultipartFile> nonEmptyFiles = Arrays
                .stream(files)
                .filter((f) -> (!f.isEmpty()))
                .collect(Collectors.toList());
        if (nonEmptyFiles.size() != files.length) {
            model.addAttribute("error", "错误, 存在空文件！");
            return "views/errors/error";
        } else {
            Arrays.stream(files).forEach((f) -> DBUtils.uploadAFileToServer(request, f));

            List<Person> personsToUpload = readXlsFromServerAndSaveToDB(request, files, session);
            logger.info("上传" + personsToUpload);

            model.addAttribute("persons", personsToUpload);
            model.addAttribute("message", "successfully uploaded " + files.length + " files");

            logger.info("Creating the Excel Sheet.");

            //生成一个Excel文件并自动下载到~/Downloads/目录下
            DBUtils.createXlsAndDownload(personsToUpload, request, response);

            String fileName = request.getServletContext().getRealPath("/data/") + DBUtils.FILE_NAME;
            model.addAttribute("fileName", fileName);
            return "views/success";
        }
    }

    @RequestMapping(value = "/uploaded/download", method = RequestMethod.GET)
    public String downloadUploaded(Model model,
                                   @RequestParam("fileName") String fileName,
                                   HttpServletResponse response) throws UnsupportedEncodingException {

        File file = new File(fileName);

        response.addHeader("Content-Disposition", "attachment;filename=" + new String(DBUtils.FILE_NAME.getBytes("UTF-8"), "ISO8859-1"));

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        OutputStream out = null;
        FileInputStream in = null;
        try {
            out = response.getOutputStream();
            in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("msg", "上传成功");
        return "redirect:/success";
    }

    public List<Person> readXlsFromUploadFiles(MultipartFile[] files, ModelMap session) {
        List<Person> persons = new ArrayList<>();
        List<Person> person = null;
        for (MultipartFile file : files) {
            try {
                person = DBUtils.readXlsFromFileName(file.getOriginalFilename());
                persons.addAll(person);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        insertPersonsToDB(persons, session);
        return person;
    }

    // 1. upload the files to server
    // 2. insert person data to db
    public List<Person> readXlsFromServerAndSaveToDB(HttpServletRequest request,
                                                     MultipartFile[] files,
                                                     ModelMap session) {
        List<Person> res = new ArrayList<>();
        for (MultipartFile file : files) {
            // 1. read the xls file
            String path = request.getServletContext().getRealPath("/data/");
            String fileName = file.getOriginalFilename();
            logger.info("file=" + path + fileName);
            try {
                List<Person> persons = DBUtils.readXlsFromFileName(path + fileName);
                res.addAll(persons);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将上传所有文档中的数据逐一插入数据库
        insertPersonsToDB(res, session);
        return res;
    }

    public void insertPersonsToDB(List<Person> persons, ModelMap session) {
        Person sn_person = (Person) session.get("user");

        Person p = null;
        Center center = null;
        String global_sn = null;
        int number = 0;
        if (sn_person != null) {
            center = centerService.findPersonInCentersByCenterid(sn_person.getIdcenter());
            logger.info(center);
            number = personService.countPersonsByIdCenter(center.getIdcenter());

        } else {
            logger.error("session does not have USER=" + session.get("user"));
            return;
        }
        for (Person person : persons) {
            Integer idperon = null;
            p = personService.findPersonByID_code(person.getID_code());

            idperon = p != null ? p.getIdperson() : idperon;

            global_sn = center.getPostcode()
                    + UNDERLINE
                    + center.getLocal_num()
                    + UNDERLINE
                    + ++number;

            if (p == null) {
                p = new Person();
                p.setOriginal_ID_code(person.getOriginal_ID_code());
                p.setID_code(PersonInfoUtils.md5(person.getOriginal_ID_code()));
                p.setBarcode(person.getBarcode());
                p.setSn_in_center(person.getSn_in_center());
                p.setIdentity(person.getIdentity());
                p.setAge(PersonInfoUtils.getAge(person.getOriginal_ID_code()));
                p.setGender(PersonInfoUtils.getGender(person.getOriginal_ID_code()));
                p.setName(person.getName().substring(0, 1) + (person.getGender().equals("男") ? "先生" : "女士"));
                p.setID_code_cut(person.getOriginal_ID_code().substring(14));
                p.setGlobal_sn(global_sn);
                p.setIdcenter(sn_person.getIdcenter());

                personService.addPerson(p);
            } else {
                global_sn = p.getGlobal_sn();
                person.setGlobal_sn(global_sn);
                personService.modifyPerson(person);
            }
            Exam exam = new Exam();
            if (idperon == null) {
                p = personService.findPersonByID_code(p.getID_code());
                idperon = p.getIdperson();
            }
            exam.setBarcode(person.getBarcode());
            exam.setIdperson(idperon);
            exam.setInput_date(ClientInfoUtils.getCurrDatetime());
            logger.info(exam);
            examService.addExam(exam);
        }
    }
}
