package com.bio.controller.auth;

import com.JsonGenerator.FetchData;
import com.JsonGenerator.element.SurveyJson;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.ClientInfoUtils;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.*;
import com.bio.service.*;
import com.jcraft.jsch.JSchException;
import com.sms.SmsBase;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@SessionAttributes({"user","username", "snAdmin", "wxuser", "sysAdmin", "vcode", "idcode"})/*单位管理员，系统管理员*/
public class Home {
    private static Logger logger = Logger.getLogger(Home.class.getName());

    private static final String ACCESS_TOKEN = "brbxyxzyz";
    @Autowired
    IPersonService personService;
    @Autowired
    ICenterService centerService;
    @Autowired
    ILoginService loginService;// need a @Service at LoginImpl.class
    @Autowired
    IAdminService adminService;
    @Autowired
    IWeChatUserService weChatUserService;

    @RequestMapping("/home")
    public ModelAndView index(ModelMap map){
        ModelAndView mv = new ModelAndView("../index");
        if (map.get("username") != null) {
            mv.addObject("username", map.get("username"));
            logger.info(map.get("username"));
        }
        if (map.get("snAdmin") != null) {
            mv.addObject("snAdmin", map.get("snAdmin"));
            logger.info(map.get("snAdmin"));
        }
        return mv;
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "views/auth/login";
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public ModelAndView login(@Param("ID_code") String ID_code,
                              @Param("name") String name,
                              ModelMap modelMap,
                              HttpServletRequest request,
                              HttpServletResponse response){
        ModelAndView mv = new ModelAndView();

        /*根据身份证号和姓名, 获取person对象*/
        Person person = personService
                                    .findPersonByID_code(
                                            PersonInfoUtils.md5(ID_code.toUpperCase())
                                    );

        if (person == null){
            mv = new ModelAndView("views/auth/login");
            mv.addObject("error", "请先完成注册!");
            logger.info("未注册用户");
            return mv;
        }
        logger.info(person);

        LoginItem loginItem = new LoginItem();
        loginItem.setIdperson(person.getIdperson());
        loginItem.setIp(ClientInfoUtils.getIpAddr(request));
        loginItem.setTime(ClientInfoUtils.getCurrDatetime());
        loginService.addLoginItem(loginItem);

        logger.info(loginItem);

        return authorityCheck(person.getIdperson(), mv, modelMap);
    }
    @RequestMapping("/survey")
    public ModelAndView generateSurveyJSON(){
        ModelAndView mv = new ModelAndView();

        logger.info("正在调用调查问卷");
        mv.setViewName("jsp/questionaire/question");
        String surveyJSON = null;
        try {
            surveyJSON = FetchData.getSurveyJSON();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        mv.addObject("surveyJSON", surveyJSON);
        return mv;
    }

    //todo:not in use
    @RequestMapping("/survey/upload")
    @ResponseBody
    public Map<String, Object> processJSONSurvey(HttpServletResponse response,
                                          HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        logger.info("Process JSONSurvey in Controller");

        return map;
    }

    @RequestMapping("/signupPage")
    public ModelAndView signUp(){
        ModelAndView mv = new ModelAndView("jsp/users/signupIdCode");
        return mv;
    }

    @RequestMapping("/signupPageFollowed")
    public ModelAndView signUpFollowed(HttpServletRequest request,
                                       String idcode,
                                       ModelMap map){
        ModelAndView mv = new ModelAndView("jsp/users/signup");
        mv.addObject("idcode", idcode);
        map.put("idcode", idcode);
        return mv;
    }
    @RequestMapping("register/idcheck")
    @ResponseBody
    public Map<String, Object> registerIdcodeCheck(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   String idcode,
                                                   String name,
                                                   ModelMap map){
        logger.info(idcode);
        String md5 = PersonInfoUtils.md5(idcode.toUpperCase());
        Person p = personService.findPersonByID_code(md5);

        logger.info(p);
        Map<String, Object> result = new HashMap<>();
        if (p != null && p.getID_code().equalsIgnoreCase(md5)) {
            result.put("result", "1");
            map.put("idcode", idcode);
        }
        else result.put("result", "0");
        return result;
    }
    //验证手机短信是否发送成功
    //1;发送成功!;1;0;1;70;7440;
    //0;失败...
    @RequestMapping("register/sms")
    @ResponseBody
    public Map<String, Object> registerSms(HttpServletRequest request, HttpServletResponse response,
                                           ModelMap map,
                                           String vcode,
                                           String phone,
                                           String idcode){
        Map<String, Object> resMap = new HashMap<>();
        logger.info("接受验证码手机号=" + phone);
        logger.info("即将发送的验证码=" + vcode);
        logger.info("身份证号=" + idcode);

        /** 短信验证码存入session(session的默认失效时间30分钟) */
        map.addAttribute("vcode", vcode);
        //测试是ok的
        Person p = personService
                .findPersonByID_code(
                        PersonInfoUtils
                                .md5(idcode.toUpperCase())
                );
        logger.info(p);

        //todo 单位选择按钮disable
        if (p == null || p.getID_code() == null){
            resMap.put("result", "-1");
            logger.error("注册用户身份证信息不在表person中");
            return resMap;
        }
        WeChatUser user = (WeChatUser) map.get("wxuser");

        resMap.put("wxuser", JSONObject.toJSONString(user));

        //todo: 添加openid, unionid处理
        if (user == null || user.getOpenid() == null || user.getOpenid().equals("")) {
            logger.warn("该用户不在Session!!");
        }else {
            logger.info("该用户在Session." );
            //todo: 测试，需要删除
            map.addAttribute("wxuser", JSONObject.toJSON(user));
            logger.info("wxuser=" + JSONObject.toJSON(user));
        }

        String requestUrl = SmsBase.URL_SMS.replace("AIMCODES", phone);
        String res = SmsBase.httpRequest(requestUrl, "GET", null, vcode);
        logger.info("http=" + res);

        if (res != null && !res.equals("")){
            if (res.startsWith("1")){//success
                resMap.put("result", "1");
            }
            else if (res.startsWith("0")) {//failure
                resMap.put("result", "0");
            }
        } else resMap.put("result", "0");
        return resMap;
    }
    @RequestMapping("register/checkVcode")
    @ResponseBody
    public Map<String, Object> registerCheckVcode(HttpServletResponse response,
                                  HttpServletRequest request,
                                  ModelMap map,
                                  String id,
                                  String vcode, String opd, String uid, String headImgUrl,
                                  String city, String province, Integer idperson,
                                  String nickname, String subs, String sub_time,
                                                  String sex, String language){
        logger.info("sessionVCode="+map.get("vcode"));
        logger.info("Actual vcode="+vcode);


        ModelAndView mv = new ModelAndView();
        Map<String, Object> resMap = new HashMap<>();
        // 获取session中存放的手机短信验证码
        String sessionVcode = (String) map.get("vcode");
        //获取idperson by id_code
        Person p = personService.findPersonByID_code(PersonInfoUtils.md5(id.toUpperCase()));
        if (sessionVcode!=null && vcode!=null){
            if  (sessionVcode == vcode || sessionVcode.equalsIgnoreCase(vcode)) {
//                // 添加注册用户到wechat表
                WeChatUser weChatUser = new WeChatUser();
                weChatUser.setIdperson(p.getIdperson());
                weChatUser.setOpenid(opd);
                weChatUser.setUnionid(uid);
                weChatUser.setCity(city);
                weChatUser.setNickname(nickname);
                weChatUser.setProvince(province);
                weChatUser.setSex(sex);
                weChatUser.setHeadImgUrl(headImgUrl);
                weChatUser.setLanguage(language);
                weChatUser.setSubscribe_time(sub_time);
                weChatUser.setSubscribe(subs);

                if (p != null && p.getIdperson() != null) weChatUser.setIdperson(idperson);

                logger.info(weChatUser);

                weChatUserService.addWxUser(weChatUser);

                resMap.put("result", 1);
            }
            else
                resMap.put("result", 0);
        } else resMap.put("result", 0);
        return resMap;
    }

    @RequestMapping("/logout")
    public String logout(@ModelAttribute("user") Person person,
                         SessionStatus sessionStatus){
        //sessionStatus中的setComplete方法可以将session中的内容全部清空
        sessionStatus.setComplete();
        //返回登陆页面
        return "views/auth/login";
    }
    @RequestMapping("/process/survey")
    @ResponseBody
    public Map<String, Object> processSurvey(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody String surveyJson
                                             ){
        Map<String, Object> map = new HashMap<>();
        logger.info(surveyJson);
        return map;
    }

    @RequestMapping("/preferences")
    public String preference(){
        return "views/errors/404";
    }

    @RequestMapping("/help/support")
    public String support(){
        return "views/errors/404";
    }

    /*404 Page Not Found*/
    @RequestMapping("*")
    public String _404PageNotFound(HttpServletRequest request){
        logger.warn("404NotFound");
        return "views/errors/404";
    }
    public ModelAndView authorityCheck(int idperson, ModelAndView mv, ModelMap map){
        logger.info(idperson);
        logger.info(mv == null);
        logger.info(map == null);

        Center center = centerService.findPersonInCentersByIdperson(idperson);
        Person person = personService.findPersonById(idperson);
        logger.info(center);
        logger.info(person);
        if (center != null && center.getIdperson() != null && center.getIdperson() == idperson){
            mv.addObject("username", person.getName());
            mv.addObject("user", person);
            mv.addObject("snAdmin", "snAdmin");

            map.put("username", person.getName());
            map.put("snAdmin", "syAdmin");
            map.put("user", person);
            mv.setViewName("../index");
            return mv;
        }

        Admin admin = adminService.findAdminUser(idperson);
        logger.info(admin);
        if (admin != null && admin.getIdperson() == idperson){
            mv.setViewName("/jsp/sys_admin/sys");
            mv.addObject("user", person);
            mv.addObject("username", person.getName());
            mv.addObject("sys_admin", "sys_admin");


            map.addAttribute("username", person.getName());
            map.addAttribute("user", person);
            map.addAttribute("sys_admin", "sys_admin");
            return mv;
        }
        //todo: 参加人员界面
        mv.setViewName("/jsp/users/userHomePage");
        mv.addObject("username", person.getName());
        mv.addObject("user", person);
        mv.addObject("nickname", person.getName());
        mv.addObject("msg", "参加人临时员界面");

        return mv;
    }
}
