package com.bio.controller.token;

import com.alibaba.fastjson.JSONObject;
import com.bio.beans.Admin;
import com.bio.beans.Center;
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
import com.wechat.utils.WeChatConstants;
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
@SessionAttributes({"user","username", "wxuser", "snAdmin", "sysAdmin", "vcode"})/*单位管理员，系统管理员*/
@Controller
public class WeChat {
    private static String ACCESS_TOKEN = "brbxyxzyz";
    private static Logger logger = Logger.getLogger(WeChat.class);

    @Autowired
    static IWeChatUserService iWeChatUserService;

    @Autowired
    static IPersonService iPersonService;

    @Autowired
    static ICenterService iCenterService;

    @Autowired
    static IAdminService iAdminService;

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
    @RequestMapping("/auth")
    public ModelAndView processCodeAndStateAndGetWxUserByAuthorization(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       ModelMap map){
        ModelAndView mv = new ModelAndView();
        String code = request.getParameter("code");
        String url = WeChatConstants.GET_WEBAUTH_URL
                .replace("APPID", WeChatConstants.appID)
                .replace("SECRET", WeChatConstants.appSecret)
                .replace("CODE", code);
        JSONObject jsonObject = WeChatUtils.httpRequest(url, "GET", null);

        if (jsonObject.getString("errcode") != null){
            mv.setViewName("views/errors/error");
            mv.addObject("error", jsonObject.getString("errmsg"));
        }

        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        String url2 = WeChatConstants.GET_WECHAT_USER_URI
                .replace("ACCESS_TOKEN", access_token)
                .replace("OPENID", openid);
        jsonObject = WeChatUtils.httpRequest(url2, "GET", null);

        if (jsonObject.getString("errcode") != null){
            mv.setViewName("views/errors/error");
            mv.addObject("error", jsonObject.getString("errmsg"));
        }
        WeChatUser user = WeChatUtils.composeWeChatUser(jsonObject);
        logger.info(user);
        //类似 /info
        return mv;
    }
    /**
     * 进行网页授权，便于获取到用户的绑定内容
     * 此为回调页面
     * reference: https://blog.csdn.net/cs_hnu_scw/article/details/79103129
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
     * @param request
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView processCodeAndStateAndFetchAccessTokenAndOpenidAndGetWxUser(HttpServletRequest request,
                                                                                    HttpServletResponse response,
                                                                                    ModelMap modelMap){
        ModelAndView mv = new ModelAndView();
        String code = request.getParameter("code");
        logger.info("code="+code);
        logger.info("state="+request.getParameter("state"));
        if (code == null || code.equals("")){
            logger.error("unauthorized wxUser ");
            mv.addObject("error", "Not Authorized.");
            mv.setViewName("views/errors/error");
            return mv;
        }

        OAuthInfo authInfo = new OAuthInfo();
        WeChatUser wxUser = null;
        String openId = null;

        //1. 通过code参数获取access_token
        authInfo = WeChatUtils.getOAuthInfoByCode(code);

        openId = authInfo.getOpenid();

        if (openId != null && !openId.equals("")) {
            wxUser = iWeChatUserService.findWxUserByOpenId(openId);

            //openid与WeChat表匹配, 登陆成功
            if (wxUser != null && wxUser.getOpenid().equals(openId)){
                //todo
                return authorityCheck(wxUser.getIdperson(), mv, modelMap, wxUser);
            }
            //不匹配
            //2. 通过access_token获取微信用户的基本信息
            wxUser = WeChatUtils.getUserByAccessTokenAndOpenId(authInfo.getAccess_token(), openId);

            logger.info(wxUser);

            if (wxUser != null && wxUser.getUnionid() != null && !wxUser.getUnionid().equals("")) {
                //ByUnionid
                WeChatUser currUser = iWeChatUserService.findWxUserByUnionid(wxUser.getUnionid());
                logger.info(currUser);
                if (currUser != null
                        && currUser.getUnionid().equals(wxUser.getUnionid())) {//db存在该用户，那么直接登陆

                    //update wechat user's openid
                    iWeChatUserService.modifyWxUser(wxUser);
                    return authorityCheck(wxUser.getIdperson(), mv, modelMap, wxUser);
                }else{// unionid不匹配，进入注册页面
                    //todo: 添加
                    mv.setViewName("jsp/users/signup");
                    mv.addObject("wxuser", wxUser);
                    return mv;
                }
            }else{ //从微信服务器获取的用户数据为空
                logger.error("从微信服务器获取的用户数据为空");
                mv.setViewName("views/errors/error");
                mv.addObject("error", "从微信服务器获取的用户数据为空");
                return mv;
            }
        }else{
            logger.warn("获取的openid无效");
            mv.setViewName("views/errors/error");
            mv.addObject("error", "openid获取为null或者空!!!");
            return mv;
        }
    }
    @RequestMapping("wx/login")
    public void wxLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          ModelMap map){
        logger.info("正在尝试微信网页扫码登陆");
        WeChatUtils.wxLoginUrl(request, response);

    }

    public static ModelAndView authorityCheck(int idperson, ModelAndView mv, ModelMap map, WeChatUser user){
        logger.info(idperson);
        logger.info(mv == null);
        logger.info(map == null);

        Center center = iCenterService.findPersonInCentersByIdperson(idperson);
        Person person = iPersonService.findPersonById(idperson);
        logger.info(center);
        logger.info(person);
        if (center != null || center.getIdperson() == idperson){
            mv.addObject("username", person.getName());
            mv.addObject("user", person);
            mv.addObject("snsAdmin", "snsAdmin");
            if (user!=null) {
                user.setIdperson(person.getIdperson());
                mv.addObject("wxuser", user);
            }

            map.put("username", person.getName());
            map.put("snsAdmin", "sysAdmin");
            map.put("user", person);
            map.put("wxuser", user);
            mv.setViewName("../index");
            return mv;
        }

        Admin admin = iAdminService.findAdminUser(idperson);
        logger.info(admin);
        if (admin != null && admin.getIdperson() == idperson){
            mv.setViewName("/jsp/sys_admin/sys");
            mv.addObject("user", person);
            mv.addObject("username", person.getName());
            mv.addObject("sys_admin", "sys_admin");

            if (user!=null) {
                user.setIdperson(person.getIdperson());
                mv.addObject("wxuser", user);
            }

            map.addAttribute("username", person.getName());
            map.addAttribute("user", person);
            map.addAttribute("sys_admin", "sys_admin");
            return mv;
        }
        //todo: 参加人员界面
        mv.setViewName("/jsp/users/userHomePage");
        mv.addObject("username", person.getName());
        mv.addObject("user", person);
        mv.addObject("msg", "参加人临时员界面");
        if (user!=null) {
            user.setIdperson(person.getIdperson());
            mv.addObject("wxuser", user);
        }

        return mv;
    }
}
