package com.bio.controller.admin;

import com.bio.Utils.DBUtils;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileUploadController{
    @Autowired
    private IPersonService personService;

    //上传文件功能
    //上传文件会自动绑定到MultipartFile中
    @RequestMapping(value = "/upFile", method = RequestMethod.POST)
    public ModelAndView upload(HttpServletRequest request,
                               @RequestParam("file") MultipartFile multipartFile) throws IOException {
        ModelAndView mv = new ModelAndView();
        if (!multipartFile.isEmpty()) {

            DBUtils.uploadSingleFile(request, multipartFile);
            mv.addObject("path", request.getSession().getServletContext().getRealPath("/data"));

            // 1. read the xls file
            String path = request.getServletContext().getRealPath("/data/");
            String fileName = multipartFile.getOriginalFilename();
            System.out.println(path+"/"+fileName);
            List<Person> persons = DBUtils.readXls(path+fileName);

            //测试
            System.out.println("uploaded person data = " + persons);
            // 2. save to db
            persons.stream().forEach((person -> personService.addPerson(person)));
            mv.addObject("persons", persons);
        } else{
            mv.addObject("message", "file is empty");
        }
        mv.setViewName("views/success");
        return mv;

    }

    // 上传多个文件
    @RequestMapping(value = "/uploadMultiFiles")
    public String uploadMultiFiles(){
        return "jsp/upload/uploadMultiFiles";
    }

    @RequestMapping(value = "/upMultiFiles", method = RequestMethod.POST)
    public ModelAndView upMultiFiles(HttpServletRequest request,
                               @RequestParam("files") MultipartFile[] files){
        ModelAndView mv = new ModelAndView();
        boolean flag = true;
        List<MultipartFile> nonEmptyFiles = Arrays.stream(files).
                                            filter((f)->(!f.isEmpty())).collect(Collectors.toList());
        // 存在空的上传文件
        if(nonEmptyFiles.size() != files.length) {
            mv.addObject("message", "错误：存在空文件！");
            mv.setViewName("views/errors/error");
        }
        else {
            // 0. 文件仅逐个上传，不做处理
            Arrays.stream(files).forEach((f) -> DBUtils.uploadSingleFile(request, f));
            //1. 插入文件中数据到db前，调取数据库中的现有person
            List<Person> allPersons = personService.findAllPersons();
            //2. 插入数据库, 添加到allPerson尾部
            allPersons.addAll(readXls(request, files));
            //调用返回下载队列成员信息表的Controller页面
            //返回信息中的原ID

            // add persons to model
            mv.addObject("persons", allPersons);
            mv.addObject("message", "successfully uploaded " + files.length + " files");

            // set view
            mv.setViewName("views/download");
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
            System.out.println(path + "/" + fileName);
            try {
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
