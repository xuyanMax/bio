package com.bio.controller.upload;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController{

    @RequestMapping(value = "/upload")
    public String uploadView(){
        return "js/upload/uploadFile";
    }
    @RequestMapping(value = "/upFile", method = RequestMethod.POST)
    public Object upload(HttpServletRequest httpServletRequest,
                         @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            //上传文件路径: bio/target
            String path = httpServletRequest.getServletContext().getRealPath("/data/");
            //上传文件名
            System.out.println(path);
            String fileName = multipartFile.getOriginalFilename();
            System.out.println(fileName);
            File filePath = new File(path, fileName);
            //判断路径是否存在，不存在则创建
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdir();
            }
            //将上传文件保存到一个目标文件中
            multipartFile.transferTo(new File(path + fileName));

            return "views/success";

        } else
            return "views/error";
    }
    @RequestMapping("/download")
    public String download(){
        return "js/download/downloadFiles";
    }
    @RequestMapping("/downloadFiles")
    public ResponseEntity<byte[]> downloadFiles(){
//        https://blog.csdn.net/qian_ch/article/details/69258465
        return null;
    }


}
