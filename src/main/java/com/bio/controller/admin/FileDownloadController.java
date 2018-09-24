package com.bio.controller.admin;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LogEvent;
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
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class FileDownloadController {

    private static Logger logger = Logger.getLogger(FileDownloadController.class);
    //下载数据库中包含所有person的Excel文件
    @RequestMapping("/download")
    public ResponseEntity<byte[]> downloadFiles(
            HttpServletRequest request) throws IOException {
//        https://blog.csdn.net/qian_ch/article/details/69258465
//        下载文件路径
        String filename = "队列成员信息表模版.xls";
        String path = request.getServletContext().getRealPath("/resource/");
        File file = new File(path + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream F： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        String browserDetails = request.getHeader("User-Agent");
        String os = "";
        String browser = "";
        logger.info(browserDetails);
        if (browserDetails.toLowerCase().indexOf("windows")>=0){
            os = "Windows";
        }else if (browserDetails.toLowerCase().indexOf("mac")>=0){
            os = "MAC-OS";
        }else if(browserDetails.toLowerCase().indexOf("x11") >= 0) {
            os = "Unix";
        } else if(browserDetails.toLowerCase().indexOf("android") >= 0) {
            os = "Android";
        } else if(browserDetails.toLowerCase().indexOf("iphone") >= 0) {
            os = "IPhone";
        }else{
            os = "UnKnown, More-Info: "+browserDetails;
        }
        String user = browserDetails;
        String userAgent = browserDetails;
        //===============Browser===========================
        if (user.contains("msie")) {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera")) {
            if(user.contains("opera"))
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if(user.contains("opr"))
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome")) {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv")) {
            browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else {
            browser = "UnKnown, More-Info: "+userAgent;
        }
        logger.info("Operating System======>"+os);
        logger.info("Browser Name==========>"+browser);

        if (os.toLowerCase().contains("ie"))
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
