package com.bio.controller.token;

import com.alibaba.fastjson.JSONObject;
import com.bio.beans.Admin;
import com.bio.beans.Person;
import com.bio.service.IAdminService;
import com.bio.service.ICenterService;
import com.bio.service.IPersonService;
import com.bio.service.IWeChatUserService;
import com.wechat.model.OAuthInfo;
import com.bio.beans.WeChatUser;
import com.wechat.utils.CheckTokenUtils;
import com.bio.Utils.ClientInfoUtils;
import com.wechat.utils.CoreService;
import com.wechat.utils.WeChatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

// references: https://blog.csdn.net/jx2931455/article/details/72833797
@SessionAttributes({"user","username", "snAdmin", "sysAdmin", "vcode"})/*单位管理员，系统管理员*/
@Controller
public class WeChat {
    private static String ACCESS_TOKEN = "brbxyxzyz";
    private static Logger logger = Logger.getLogger(WeChat.class);

    @Autowired
    IWeChatUserService iWeChatUserService;

    @Autowired
    IPersonService iPersonService;

    @Autowired
    ICenterService iCenterService;

    @Autowired
    IAdminService iAdminService;

    /**
     * 确认请求来自微信服务器
     */
    @RequestMapping(value = "/wx/token/get")
    public void get(HttpServletRequest request,
                    HttpServletResponse response) throws NoSuchAlgorithmException, IOException, ServletException {

        request.setCharacterEncoding("UTF-8");// 将请求、响应的编码均设置为UTF-8（防止中文乱码)
        response.setCharacterEncoding("UTF-8");

        logger.info("http request: wx/token/get: " + request.getRequestURL().toString());

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
                    logger.info("token verification: A REQUEST FROM WECHAT!" );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.error("WeChat token verification failed!");
            logger.error("WeChat server address: " + ClientInfoUtils.getIpAddr(request));
        }else {
            logger.info("message request from wechat.");
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
     * 进行网页授权，便于获取到用户的绑定内容
     * 此为回调页面
     * reference: https://blog.csdn.net/cs_hnu_scw/article/details/79103129
     * @param request
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView getOpenId(HttpServletRequest request,
                                  HttpServletResponse response,
                                  ModelMap modelMap){
        ModelAndView mv = new ModelAndView();
        String code = request.getParameter("code");
        logger.info("code="+code);
        logger.info("state="+request.getParameter("state"));
        if (code == null || code.equals("")){
            logger.error("unauthorized user ");
            mv.addObject("error", "Not Authorized.");
            mv.setViewName("views/errors/error");
            return mv;
        }

        OAuthInfo authInfo = new OAuthInfo();
        WeChatUser user = null;
        String openId = null;

        //1. 通过code参数获取access_token
        authInfo = WeChatUtils.getOAuthInfoByCode(code);

        openId = authInfo.getOpenid();

        if (openId != null && !openId.equals("")) {
            //2. 通过access_token获取用户的基本信息
            user = WeChatUtils.getUserByAccessTokenAndOpenId(authInfo.getAccess_token(), openId);
            logger.info(user);
            if (user != null && user.getOpenid() != null && !user.getOpenid().equals("")) {
                //todo: unionid | openid
                WeChatUser currUser = iWeChatUserService.findWxUserByOpenId(user.getOpenid());
                logger.info(currUser);
                if (currUser != null) {//db存在该用户，那么直接登陆
                    //todo: 判断身份[可以单拎出来]
                    //1. 判断是否为单位管理员
                    //2. 是否为系统管理员
                    //3. 是普通用户
                    Person p = iPersonService.findPersonById(user.getIdperson());
                    return authorityCheck(p.getIdcenter(), mv, p, modelMap, user);
                }else{//没在数据库中, 新注册用户，如何处理？？
                    Person p = new Person();

                    //todo:
                    p.setID_code(user.getOpenid());
                    p.setName(user.getNickname());

                    iPersonService.addPerson(p);

                    p = iPersonService.findPersonByID_code(p.getID_code());

                    user.setIdperson(p.getIdperson());


                    iWeChatUserService.addWxUser(user);

                    // todo: 判断用户权限
                    return authorityCheck(p.getIdcenter(), mv, p, modelMap, user);
                }

            }else{ //从微信服务器获取的用户数据为空
                logger.error("从微信服务器获取的用户数据为空");
                mv.setViewName("views/errors/error");
                return mv;
            }
        }else{
            logger.warn("获取的openId无效");
        }
        mv.setViewName("views/errors/error");
        mv.addObject("error", "openid获取为null或者空!!!");
        return mv;
    }
    @RequestMapping("wx/login")
    public void wxLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          ModelMap map){
        //send a http request, wx QR login page
        logger.info("Start scanning authorization");
        WeChatUtils.wxLoginUrl(request, response);

        logger.info("正在微信网页扫码登陆");

    }
    public ModelAndView authorityCheck(Integer idcenter, ModelAndView mv, Person p, ModelMap modelMap, WeChatUser user){
        if (idcenter != null){
            int cnt = iCenterService.findPersonInCentersByCenterid(p.getIdcenter());
            if (cnt > 0){
                logger.info("user[name="+p.getName()+"]=local admin");
                mv.setViewName("redirect:/home");
                mv.addObject("user", user);
                modelMap.addAttribute("username", p.getName());
                modelMap.addAttribute("snAdmin", "snAdmin");
                return mv;
            }
        }
        // 判断sysAdmin身份
        Admin admin  = iAdminService.selectAdminUser(p.getIdperson());
        if (admin != null){
            logger.info("Checking if login user's authority IS system admin");
            mv = new ModelAndView("/jsp/sys_admin/sys");
            modelMap.addAttribute("username", p.getName());
            mv.addObject("user", user);
            modelMap.addAttribute("sysAdmin",  admin.getIdadmin());
            return mv;
        }
        /*否则, 为普通用户*/
        mv = new ModelAndView("/jsp/users/userHomePage");
        modelMap.addAttribute("username", p.getName());
        modelMap.addAttribute("user", user);
        logger.info("IS a normal user");
        return mv;

    }
}
