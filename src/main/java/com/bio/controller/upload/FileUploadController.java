package com.bio.controller.upload;

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
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest httpServletRequest,
                         @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            //上传文件路径
            String path = httpServletRequest.getServletContext().getRealPath("/data/");
            //上传文件名
            String fileName = multipartFile.getOriginalFilename();
            File filePath = new File(path, fileName);
            //判断路径是否存在，不存在则创建
            if(!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdir();
            }
            //将上传文件保存到一个目标文件中
            multipartFile.transferTo(new File(path + File.pathSeparator + fileName));

            return "userInfo";

        }else
            return "error";

    }

}
