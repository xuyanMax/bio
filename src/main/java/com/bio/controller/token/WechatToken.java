package com.bio.controller.token;

import com.bio.Utils.CheckTokenUtils;
import com.bio.Utils.ClientInfoUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

// references: https://blog.csdn.net/jx2931455/article/details/72833797
@Controller
@RequestMapping("/wx/token")
public class WechatToken {
    private static final String ACCESS_TOKEN = "brbxyxzyz";

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void get(HttpServletRequest request,
                    HttpServletResponse response) throws NoSuchAlgorithmException {
        if (request.getMethod().toLowerCase().equals("get")){
            //微信加密签名
            String signature = request.getParameter("signature");
            //时间戳
            String timestamp = request.getParameter("timestamp");
            //随机数
            String nonce = request.getParameter("nonce");
            //随机字符串
            String echostr = request.getParameter("echostr");

            //通过验证Signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature!=null && CheckTokenUtils.checkSignature(ACCESS_TOKEN, timestamp, nonce, signature)){
                try {
                    PrintWriter printWriter = response.getWriter();
                    printWriter.print(echostr);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            /*测试获取ip地址*/
            System.out.println(ClientInfoUtils.getIpAddr(request));
            /*测试结束*/
        }
    }

}
