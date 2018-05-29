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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileUploadController{
    @Autowired
    private IPersonService personService;

    @RequestMapping(value = "/upload")
    public String uploadView(){
        return "jsp/upload/uploadFile";
    }

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
//            System.out.println(path+"/"+fileName);
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
                               @RequestParam("files") MultipartFile[] multipartFiles){
        ModelAndView mv = new ModelAndView();
        boolean flag = true;
        List<MultipartFile> nonEmptyFiles = Arrays.stream(multipartFiles).
                                            filter((f)->(!f.isEmpty())).collect(Collectors.toList());
        // 存在空的上传文件
        if(nonEmptyFiles.size() != multipartFiles.length)
            mv.addObject("message", "错误：存在空文件！");
        else {
            Arrays.stream(multipartFiles).forEach((f) -> DBUtils.uploadSingleFile(request, f));
            mv.addObject("message", "successfully uploaded " + multipartFiles.length + " files");
        }
        mv.setViewName("views/success");
        return mv;
    }




}
