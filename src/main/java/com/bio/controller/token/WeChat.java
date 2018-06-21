package com.bio.controller.token;

import com.wechat.model.WeChatUser;
import com.wechat.utils.AccessTokenUtil;
import com.wechat.utils.CheckTokenUtils;
import com.bio.Utils.ClientInfoUtils;
import com.wechat.utils.CoreService;
import com.wechat.utils.WeChatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

// references: https://blog.csdn.net/jx2931455/article/details/72833797
@Controller
public class WeChat {
    private static String ACCESS_TOKEN = "brbxyxzyz";

    /**
     * 确认请求来自微信服务器
     */
    @RequestMapping(value = "/wx/token/get")
    public void get(HttpServletRequest request,
                    HttpServletResponse response) throws NoSuchAlgorithmException, IOException, ServletException {

        request.setCharacterEncoding("UTF-8");// 将请求、响应的编码均设置为UTF-8（防止中文乱码)
        response.setCharacterEncoding("UTF-8");
        /*测试*/
        System.out.println("访问Controller: wx/token/get: " + request.getRequestURL().toString());
        System.out.println("@Controller: WeChat, 请求使用的方法: " + request.getMethod());

        if (request.getMethod().toLowerCase().equals("get")){// REQUEST.METHOD = GET
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
                    System.out.println("TOKEN验证: 确认来自微信的请求!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*测试获取ip地址*/
            System.out.println("验证失败!!!");
            System.out.println(ClientInfoUtils.getIpAddr(request));
            /*测试结束*/
        }else {
            System.out.println(request.getContextPath());
            request.getRequestDispatcher("/wx/rec/msg").forward(request, response);
        }
    }
    /*微信消息接口测试*/
    @RequestMapping("/wx/rec/msg")
    public void receiveMessage(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        // 调用核心业务类接收消息、处理消息
        String respMsg = CoreService.processRequest(request);

        //响应消息
        PrintWriter pw = response.getWriter();
        pw.print(respMsg);
        pw.close();

    }
    /**
     * 进行网页授权，便于获取到用户的绑定的内容
     * 此为回调页面
     * reference: https://blog.csdn.net/cs_hnu_scw/article/details/79103129
     * @param request
     * @return
     */
    @RequestMapping("/user/info")
    public String getOpenId(HttpServletRequest request,
                            HttpServletResponse response){
        // todo:
        WeChatUser user = new WeChatUser();

        /**
         * 进行获取openId，必须的一个参数，这个是当进行了授权页面的时候，再重定向了我们自己的一个页面的时候，
         * 会在request页面中，新增这个字段信息，
         */
        System.out.println(request.getParameter("code"));
        System.out.println(request.getParameter("openId"));

        return  user.getOpenId();
    }
}
