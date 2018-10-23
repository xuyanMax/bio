package com.bio.controller.token;

import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.PersonInfoUtils;
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

@SessionAttributes({"user", "username", "wxuser", "snAdmin", "sysAdmin", "vcode"})/*单位管理员，系统管理员*/
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

        logger.info("Request from WeChat=" + request.getRequestURL().toString());

        if (request.getMethod().toLowerCase().equals("get")) {// REQUEST.METHOD = GET
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
            if (signature != null && CheckTokenUtils.checkSignature(ACCESS_TOKEN, timestamp, nonce, signature)) {
                try {
                    printWriter = response.getWriter();
                    printWriter.print(echostr);
                    printWriter.flush();
                    printWriter.close();
                    logger.info("token verification: A REQUEST FROM WECHAT!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.error("WeChat token verification failed!");
            logger.error("WeChat server address: " + ClientInfoUtils.getIpAddr(request));
        } else {
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
                                                                       ModelMap map) {
        ModelAndView mv = new ModelAndView();
        String code = request.getParameter("code");
        logger.info(code);
        String url = WeChatConstants.GET_WEBAUTH_URL
                .replace("APPID", WeChatConstants.appID)
                .replace("APP_SECRET", WeChatConstants.appSecret)
                .replace("CODE", code);
        logger.info(url);
        JSONObject JsonWxUser = WeChatUtils.httpRequest(url, "GET", null);

        logger.info(JsonWxUser);

        if (JsonWxUser.getString("errcode") != null) {
            mv.setViewName("views/errors/error");
            mv.addObject("error", JsonWxUser);
            return mv;
        }

        String access_token = JsonWxUser.getString("access_token");
        String openid = JsonWxUser.getString("openid");

        String url2 = WeChatConstants.GET_WECHAT_USER_URI
                .replace("ACCESS_TOKEN", access_token)
                .replace("OPENID", openid);

        logger.info(url2);

        JsonWxUser = WeChatUtils.httpRequest(url2, "GET", null);

        logger.info(JsonWxUser);

        if (JsonWxUser.getString("errcode") != null) {
            mv.setViewName("views/errors/error");
            mv.addObject("error", JsonWxUser.getString("errmsg"));
            logger.error(JsonWxUser);
            return mv;
        }

        WeChatUser wxuser = iWeChatUserService.findWxUserByOpenId(JsonWxUser.getString("openid"));
        logger.info(wxuser);
        if (wxuser != null && wxuser.getIdperson() != null) {
            return loginAuthCheck(wxuser.getIdperson(), mv, map, wxuser);
        } else {

//            String url3 = WeChatConstants.GET_WXUSER_BY_OPENID_ACCESS_TOKEN
//                    .replace("OPENID", openid)
//                    .replace("ACCESS_TOKEN", access_token);
//
//            JsonWxUser = WeChatUtils.httpRequest(url 3, "GET", null);
            logger.info("Trying unionid");
            wxuser = iWeChatUserService.findWxUserByUnionid(JsonWxUser.getString("unionid"));
            if (wxuser == null || wxuser.getIdperson() == null) {
                if (JsonWxUser.getString("errcode") != null) {
                    mv.addObject("error", JsonWxUser.getString("errmsg"));
                    mv.setViewName("views/errors/error");
                    return mv;
                } else {
                    logger.info("扫码登录=>openid和unionid不匹配，将进入注册页");
                    wxuser = WeChatUtils.composeWeChatUser(JsonWxUser);
                    mv.setViewName("jsp/users/signupIdCode");
                    mv.addObject("wxuser", wxuser);
                    map.addAttribute("wxuser", wxuser);
                    return mv;
                }
            } else {
                return loginAuthCheck(wxuser.getIdperson(), mv, map, wxuser);
            }
        }

    }

    /**
     * 进行网页授权，便于获取到用户的绑定内容
     * 此为回调页面
     * reference: https://blog.csdn.net/cs_hnu_scw/article/details/79103129
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
     *
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView processCodeAndStateAndFetchAccessTokenAndOpenidAndGetWxUser(HttpServletRequest request,
                                                                                    HttpServletResponse response,
                                                                                    ModelMap modelMap) {
        ModelAndView mv = new ModelAndView();
        String code = request.getParameter("code");

        logger.info("code=" + code);
        logger.info("state=" + request.getParameter("state"));

        if (code == null || code.equals("")) {
            logger.error("unauthorized wxUser ");
            mv.addObject("error", "Not Authorized.");
            mv.setViewName("views/errors/error");
            return mv;
        }

        OAuthInfo authInfo = null;
        WeChatUser wxUser = null;

        //1. 通过code参数获取access_token={openid:, access_token:, unionid:, ...}
        authInfo = WeChatUtils.getOAuthInfoByCode(code);

        String openid = authInfo.getOpenid();
        //测试openid是否匹配
        if (openid != null && !openid.equals("")) {
            wxUser = iWeChatUserService.findWxUserByOpenId(openid);
            if (wxUser != null && wxUser.getOpenid().equals(openid)) {
                logger.info(wxUser);
                logger.info("扫码登陆openid匹配");
                return loginAuthCheck(wxUser.getIdperson(), mv, modelMap, wxUser);
            }
            //2. 通过access_token获取微信用户的基本信息，  unionid
            wxUser = WeChatUtils.getUserByAccessTokenAndOpenId(authInfo.getAccess_token(), openid);

            if (wxUser != null && wxUser.getUnionid() != null && !wxUser.getUnionid().equals("")) {

                //idperson暂为空
                logger.info(wxUser);
                WeChatUser dbUser = iWeChatUserService.findWxUserByUnionid(wxUser.getUnionid());

                if (dbUser != null
                        && dbUser.getUnionid().equals(wxUser.getUnionid())) {//db存在该用户，那么直接登陆

                    logger.info(dbUser);
                    logger.info("扫码登陆unionid匹配!");
                    //update wechat user's openid etc.
                    iWeChatUserService.modifyWxUserByUnionid(wxUser);
                    return loginAuthCheck(wxUser.getIdperson(), mv, modelMap, wxUser);
                } else {
                    logger.info("扫码登陆openid和unionid不匹配，即将进入注册页");
                    mv.setViewName("jsp/users/signupIdCode");

                    mv.addObject("wxuser", wxUser);
                    modelMap.addAttribute("wxuser", wxUser);
                    return mv;
                }
            } else {
                logger.error("从微信服务器获取的用户数据为空");
                mv.setViewName("views/errors/error");
                mv.addObject("error", "从微信服务器获取的用户数据为空");
                return mv;
            }
        } else {
            logger.warn("获取的openid无效");
            mv.setViewName("views/errors/error");
            mv.addObject("error", "openid获取为null或者空!!!");
            return mv;
        }
    }

    @RequestMapping("wx/login")
    public void wxLogin(HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap map) {
        logger.info("正在微信网页扫码登陆");
        WeChatUtils.wxLoginUrl(request, response);

    }

    public ModelAndView loginAuthCheck(int idperson, ModelAndView mv, ModelMap session, WeChatUser user) {
        logger.info("idperson=" + idperson + ", mv=" + mv + ", modelmap=" + session);

        Center center = iCenterService.findPersonInCentersByIdperson(idperson);
        Person person = iPersonService.findPersonByIdperson(idperson);
        logger.info(center);
        logger.info(person);
        if (center != null && center.getIdperson() == idperson) {
            mv.addObject("username", person.getName());
            mv.addObject("user", person);
            mv.addObject("snsAdmin", "snsAdmin");
            if (user != null) {
                user.setIdperson(person.getIdperson());
                mv.addObject("wxuser", user);
            }

            session.put("username", person.getName());
            session.put("snAdmin", "snAdmin");
            session.put("user", person);
            session.put("wxuser", user);
            mv.setViewName("jsp/sys_admin/sys");
            return mv;
        }

        Admin admin = iAdminService.findAdminUser(idperson);
        logger.info(admin);
        if (admin != null && admin.getIdperson() == idperson) {
            mv.setViewName("/jsp/sys_admin/sys");
            mv.addObject("user", person);
            mv.addObject("username", person.getName());
            mv.addObject("sys_admin", "sys_admin");

            if (user != null) {
                user.setIdperson(person.getIdperson());
                mv.addObject("wxuser", user);
            }

            session.addAttribute("username", person.getName());
            session.addAttribute("user", person);
            session.addAttribute("sys_admin", "sys_admin");
            return mv;
        }
        mv.setViewName("/jsp/users/userHomePage");
        mv.addObject("username", person.getName());
        mv.addObject("user", person);
        mv.addObject("msg", "参加人员界面");
        if (user != null) {
            user.setIdperson(person.getIdperson());
            mv.addObject("wxuser", user);
        }

        return mv;
    }

    //===================单元测试
    @RequestMapping("testLocal")
    public ModelAndView test(ModelMap map) {
        ModelAndView mv = new ModelAndView();
        WeChatUser user = iWeChatUserService.findWxUserByOpenId("oJXrv0lCVwavIP1VTQVRD-HDrv08");
        iWeChatUserService.addWxUser(user);
        logger.info(user);
        return loginAuthCheck(user.getIdperson(), mv, map, user);
    }

    @RequestMapping("testSignup")
    public ModelAndView testSignup(ModelMap map) {
        WeChatUser wxuser = new WeChatUser();
        wxuser.setIdwechat(999);
        wxuser.setUnionid("123");
        wxuser.setOpenid("abx");
        wxuser.setSubscribe("1100420");
        wxuser.setSubscribe_time("123123123123)))");
        wxuser.setLanguage("zn");
        wxuser.setHeadImgUrl("http://***.com");
        wxuser.setNickname("xyx");
        wxuser.setIdperson(308);
        wxuser.setSex("男");
        wxuser.setCity("石家庄");
        wxuser.setProvince("河北省");
        map.addAttribute("wxuser", wxuser);
        ModelAndView mv = new ModelAndView("jsp/users/signupIdCode");
        return mv;

    }

    @RequestMapping("testVcode")
    public ModelAndView testVcode(ModelMap session) {
        WeChatUser user = iWeChatUserService.findWxUserByOpenId("abx");
        session.addAttribute("wxuser", user);
        session.addAttribute("idcode", "13010419920518241X");
        ModelAndView mv = new ModelAndView("jsp/users/signup");
        mv.addObject("idcode", "13010419920518241X");
        return mv;
    }

    @RequestMapping("testGetPeronByIdAndIdcenter")
    public String testGetPeronByIdAndIdcenter(ModelMap session) {
        Person p = iPersonService.findPersonByID_codeAndIdcenter(PersonInfoUtils.md5("13010419920518241X"), 1);
        logger.info(p);
        return "../index";
    }
}
