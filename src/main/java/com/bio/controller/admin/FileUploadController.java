package com.bio.controller.admin;

import com.bio.Utils.DBUtils;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@SessionAttributes({"username", "user"})// 此处定义此Controller中将要创建和使用哪些session中的对象名
public class FileUploadController{
    @Autowired
    private IPersonService personService;

    // 上传多个文件
    @RequestMapping(value = "/uploadMultiFiles")
    public ModelAndView uploadMultiFiles(@ModelAttribute("username") String username){
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", username);
        mv.setViewName("jsp/upload/uploadMultiFiles");
        return mv;
    }

    @RequestMapping(value = "/upMultiFiles", method = RequestMethod.POST)
    public ModelAndView upMultiFiles(HttpServletRequest request,
                               @RequestParam("files") MultipartFile[] files){
        ModelAndView mv = new ModelAndView();
        List<MultipartFile> nonEmptyFiles = Arrays.stream(files).
                                            filter((f)->(!f.isEmpty())).collect(Collectors.toList());
        // 存在空的上传文件
        if(nonEmptyFiles.size() != files.length) {
            mv.addObject("message", "错误, 存在空文件！");
            mv.setViewName("views/errors/error");
        }
        else {
            // 0. 多文件逐个上传到服务器，不上传到db
            // todo:如果不需要上传，则略过这一步
            Arrays.stream(files).forEach((f) -> DBUtils.uploadSingleFile(request, f));

            //1. 插入文件中数据到db前，调取数据库中的现有person
            List<Person> allPersons = personService.findAllPersons();

            // test
            System.out.println("数据库中user数量: " + allPersons.size());
            System.out.println("等待上传的user: /n" + readXls(request, files));

            //2. 插入数据库的数据，从上传到server的文件中读取
            // todo: 文件是否不需要上传到Server
            List<Person> personsToUpload = readXls(request, files);
            //set age, gender
            personsToUpload.stream().forEach(p->{
                p.setGender(PersonInfoUtils.getGender(p.getID_code()));
                p.setAge(PersonInfoUtils.getAge(p.getID_code()));
            });
            // 3. 添加到allPerson尾部
            allPersons.addAll(personsToUpload);
            // todo:??无法获取数据库中person的原身份证号
            String tmp_ID_code = "13010419920518241X";
            allPersons.stream().forEach(p->p.setOriginal_ID_code(tmp_ID_code));

            //调用返回下载队列成员信息表的Controller页面
            //返回信息中的原ID

            // add persons to model
            mv.addObject("persons", allPersons);
            mv.addObject("message", "successfully uploaded " + files.length + " files");
            //test
            System.out.println("creating excel sheet..");
            //
            DBUtils.createExcelSheet(personsToUpload);

            // set view
            mv.setViewName("views/success");
        }

        return mv;
    }
    // 1. upload the files
    // 2. insert person data to db

    public List<Person> readXls(HttpServletRequest request,
                                MultipartFile[] files){
        List<Person> res = new ArrayList<>();
        for (MultipartFile file:files) {
            // 1. read the xls file
            String path = request.getServletContext().getRealPath("/data/");
            String fileName = file.getOriginalFilename();
            System.out.println(path + fileName);
            try {
                //从Server读取files
                List<Person> persons = DBUtils.readXls(path + fileName);
                res.addAll(persons);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将上传所有文档中的数据逐一插入数据库
        res.stream().forEach(person -> personService.addPerson(person));
        return res;
    }
}
