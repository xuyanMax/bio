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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"username", "user", "wxuser"})// 此处定义此Controller中将要创建和使用哪些session中的对象名
public class FileUploadController{
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
    public ModelAndView uploadMultiFiles(@ModelAttribute("username") String username){
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", username);
        mv.setViewName("jsp/upload/uploadFiles");
        return mv;
    }

    @RequestMapping(value = "/upMultiFiles", method = RequestMethod.POST)
    public ModelAndView upMultiFiles(HttpServletRequest request,
                               @RequestParam("files") MultipartFile[] files, ModelMap session){
        ModelAndView mv = new ModelAndView();
        List<MultipartFile> nonEmptyFiles = Arrays
                                                .stream(files)
                                                .filter((f)->(!f.isEmpty()))
                                                .collect(Collectors.toList());
        if(nonEmptyFiles.size() != files.length) {
            mv.addObject("error", "错误, 存在空文件！");
            mv.setViewName("views/errors/error");
        }
        else {
            Arrays.stream(files).forEach((f) -> DBUtils.uploadAFileToServer(request, f));

            List<Person> personsToUpload = readXlsFromServerAndSaveToDB(request, files, session);
            logger.info("上传" + personsToUpload);

            mv.addObject("persons", personsToUpload);
            mv.addObject("message", "successfully uploaded " + files.length + " files");

            logger.info("Creating the Excel Sheet.");

            //生成一个Excel文件并自动下载到~/Downloads/目录下
            DBUtils.createXlsAndDownload(personsToUpload);
            mv.setViewName("views/success");
        }

        return mv;
    }
    // 1. upload the files to server
    // 2. insert person data to db
    public List<Person> readXlsFromServerAndSaveToDB(HttpServletRequest request,
                                                     MultipartFile[] files,
                                                     ModelMap session){
        List<Person> res = new ArrayList<>();
        for (MultipartFile file:files) {
            // 1. read the xls file
            String path = request.getServletContext().getRealPath("/data/");
            String fileName = file.getOriginalFilename();
            logger.info("file=" + path + fileName);
            try {
                List<Person> persons = DBUtils.readXlsFromServer(path + fileName);
                res.addAll(persons);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将上传所有文档中的数据逐一插入数据库
        personToDB(res, session);
        return res;
    }

    public void personToDB(List<Person> persons, ModelMap session){
        Person sn_person = (Person) session.get("user");
        Person p = null;
        String global_sn = null;
        if (sn_person != null){
            Center center = centerService.findPersonInCentersByCenterid(sn_person.getIdcenter());
            int number = personService.countPersonsByIdCenter(center.getIdcenter());
            global_sn = center.getPostcode()
                            + UNDERLINE
                            + center.getLocal_num()
                            + UNDERLINE
                            + number;

        }else{//todo: 测试session用
            logger.error("session does not have USER=" + session.get("user"));
            return;
        }
        for (Person person:persons){
            p = personService.findPersonByID_code(person.getID_code());
            if (p == null){
                p = new Person();
                p.setOriginal_ID_code(p.getOriginal_ID_code());
                p.setID_code(PersonInfoUtils.md5(person.getOriginal_ID_code()));
                p.setBarcode(person.getBarcode());
                p.setName(person.getName());
                p.setSn_in_center(person.getSn_in_center());
                p.setRelative(person.getRelative());
                p.setAge(PersonInfoUtils.getAge(person.getOriginal_ID_code()));
                p.setGender(PersonInfoUtils.getGender(person.getOriginal_ID_code()));
                p.setID_code_cut(person.getOriginal_ID_code().substring(15));
                p.setGlobal_sn(global_sn);

                logger.info(p);

                personService.addPerson(p);
            }else {
                global_sn = p.getGlobal_sn();
                person.setGlobal_sn(global_sn);

                logger.info(person);

                personService.modifyPerson(person);
            }
            Exam exam = new Exam();
            exam.setBarcode(p.getBarcode());
            exam.setIdperson(p.getIdperson());
            exam.setInput_date(ClientInfoUtils.getCurrDatetime());
//            exam.setSup2();
            logger.info(exam);
            examService.addExam(exam);
        }
    }
}
