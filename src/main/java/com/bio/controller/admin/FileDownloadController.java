package com.bio.controller.admin;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Controller
public class FileDownloadController {
    //跳转
    @RequestMapping("/download")
    public String download(){
        return "jsp/download/downloadFiles";
    }

    //下载上传文件
    @RequestMapping("/downloadFiles")
    public ResponseEntity<byte[]> downloadFiles(
            HttpServletRequest request,
            @RequestParam("file") String filename,
            Model model) throws IOException {
//        https://blog.csdn.net/qian_ch/article/details/69258465
//下载文件路径
        String path = request.getServletContext().getRealPath("/data/");
        File file = new File(path + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/list")
    public String listFiles(HttpServletRequest request,
                            ModelAndView mv,
                            HttpServletResponse response){
        String filesPath = request.getSession().getServletContext().getRealPath("/data/");
        File[] files = new File(filesPath).listFiles();

        //返回包含上传文件列表的字符串
        String res = Arrays.stream(files).
                            map(File::getName).
                            reduce((f1, f2)->(f1+"\n"+f2)).
                            get();
        mv.addObject("files", res);
        return "views/success";
    }

}
