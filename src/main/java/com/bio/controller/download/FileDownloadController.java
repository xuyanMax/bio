package com.bio.controller.download;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public class FileDownloadController {
    //跳转
    @RequestMapping("/download")
    public String download(){
        return "js/download/downloadFiles";
    }

    //下载上传文件
    @RequestMapping("/downloadFiles")
    public ResponseEntity<byte[]> downloadFiles(
            HttpServletRequest request,
            @RequestParam("file") String file,
            Model model){
//        https://blog.csdn.net/qian_ch/article/details/69258465


        return null;
    }

}
