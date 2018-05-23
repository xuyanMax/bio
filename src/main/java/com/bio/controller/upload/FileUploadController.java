package com.bio.controller.upload;

import com.bio.Utils.Utils;
import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    //跳转
    @RequestMapping(value = "/upload")
    public String uploadView(){
        return "js/upload/uploadFile";
    }

    //上传文件功能
    //上传文件会自动绑定到MultipartFile中
    @RequestMapping(value = "/upFile", method = RequestMethod.POST)
    public String upload(HttpServletRequest httpServletRequest,
                         @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            Utils.uploadSingleFile(httpServletRequest, multipartFile);
            return "views/success";
        } else
            return "views/error";
    }

    @RequestMapping(value = "/uploadMultiFiles")
    public String uploadMultiFiles(){
        return "js/upload/uploadMultiFiles";
    }

    @RequestMapping(value = "/upMultiFiles", method = RequestMethod.POST)
    public String upMultiFiles(HttpServletRequest request,
                               @RequestParam("file") MultipartFile[] multipartFiles){
        boolean flag = true;
        List<MultipartFile> nonEmptyFiles = Arrays.stream(multipartFiles).
                                            filter((f)->(!f.isEmpty())).collect(Collectors.toList());
        // 存在空的上传文件
        if(nonEmptyFiles.size() != multipartFiles.length)
            return "views/error";
        else
            Arrays.stream(multipartFiles).forEach((f)-> Utils.uploadSingleFile(request, f));
        return "views/success";
    }


    // todo: 插入person数据
    public void insertPerson(Person person){
        personService.addPerson(person);
    }

}
