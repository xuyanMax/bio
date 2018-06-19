package com.bio.controller.token;

import com.wechat.utils.CheckTokenUtils;
import com.bio.Utils.ClientInfoUtils;
import com.bio.service.IWeChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

// references: https://blog.csdn.net/jx2931455/article/details/72833797
@Controller
public class WeChatToken {
    private static String ACCESS_TOKEN = "brbxyxzyz";

    @Resource
    IWeChatService weChatService;

    @RequestMapping(value = "wx/token/get", method = RequestMethod.GET)
    public void get(HttpServletRequest request,
                    HttpServletResponse response) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");// 将请求、响应的编码均设置为UTF-8（防止中文乱码)
        response.setCharacterEncoding("UTF-8");

        if (request.getMethod().toLowerCase().equals("get")){
            //微信加密签名
            String signature = request.getParameter("signature");
            //时间戳
            String timestamp = request.getParameter("timestamp");
            //随机数
            String nonce = request.getParameter("nonce");
            //随机字符串
            String echostr = request.getParameter("echostr");

            PrintWriter printWriter = null;
            //通过验证Signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature!=null && CheckTokenUtils.checkSignature(ACCESS_TOKEN, timestamp, nonce, signature)){
                try {
                    printWriter = response.getWriter();
                    printWriter.print(echostr);
                    printWriter.flush();
                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            /*测试获取ip地址*/
            System.out.println(ClientInfoUtils.getIpAddr(request));
            /*测试结束*/

        }else { //
            System.out.println(request.getContextPath());
            String respMsg = weChatService.wechatPost(request);
            PrintWriter printWriter = null;
            try {
                printWriter = response.getWriter();
                printWriter.write(respMsg);
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                printWriter.close();
            }
        }
    }


}
