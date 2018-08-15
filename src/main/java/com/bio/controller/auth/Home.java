package com.bio.controller.auth;

import com.JsonGenerator.FetchData;
import com.bio.Utils.ClientInfoUtils;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Admin;
import com.bio.beans.LoginItem;
import com.bio.beans.Person;
import com.bio.service.IAdminService;
import com.bio.service.ICenterService;
import com.bio.service.ILoginService;
import com.bio.service.IPersonService;
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
@SessionAttributes({"user","username", "snAdmin", "sysAdmin", "vcode"})/*单位管理员，系统管理员*/
public class Home {
//    todo: Logger
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
        ModelAndView mv = null;

        /*根据身份证号和姓名, 获取person对象*/
        Person person = personService
                                    .findPersonByID_code(
                                            PersonInfoUtils.md5(ID_code.toLowerCase())
                                    );

        if (person == null){
            mv = new ModelAndView("views/auth/login");
            mv.addObject("error", "非合法用户!!!");
            /*logger*/
            logger.info("非法用户尝试登陆!");
            return mv;
        }
        logger.info(person);

        /*组装login item*/
        LoginItem loginItem = new LoginItem();
        loginItem.setIdperson(person.getIdperson());
        loginItem.setIp(ClientInfoUtils.getIpAddr(request));
        loginItem.setTime(ClientInfoUtils.getCurrDatetime());
        loginService.addLoginItem(loginItem);
        /*组装结束*/

        /*首先, 判断登陆用户是否为单位管理员*/
        /*关联关系查询 sn_in_center在表centers中存在*/
        Integer idcenter = person.getIdcenter();
        String pname = person.getName();

        logger.info("login user's idcenter="+idcenter);
        logger.info("login user's name="+pname);

         /*关联关系查询*/
        if (idcenter != null){
            logger.info("Checking if login user's authority is local admin");
            //通过idcenter判断，该用户是否在centers表中，在则是单位管理员，否则不是单位管理员，可能为普通用户或系统管理员
            int cnt = centerService.findPersonInCentersByCenterid(idcenter);
            logger.info("return findPersonInCentersByCenterid()="+cnt);
            // todo: 本单位内查重??
            if (cnt > 0) {// 说明其sn_in_center在centers表中存在
                logger.info("login user's authority IS local admin");
                mv = new ModelAndView("redirect:/home");
                mv.addObject("user", person);
                mv.addObject("username", person.getName());
                mv.addObject("snAdmin", "snAdmin");
                /*添加session attribute*/
                modelMap.addAttribute("user", person);
                modelMap.addAttribute("username", person.getName());
                modelMap.addAttribute("snAdmin", "snAdmin");
                //前往snAdmin主页面
                return mv;
            }
        }
        /**
        * 接下来, 判断用户是否为系统管理员
        * */
        //关联关系查询 idperson是否在表admin中存在
        Admin admin  = adminService.selectAdminUser(person.getIdperson());
        if (admin !=null && admin.getIdperson() == person.getIdperson()){
            // go to sysAdmin home page
            logger.info("Checking if login user's authority IS system admin");
            mv = new ModelAndView("/jsp/sys_admin/sys");
            modelMap.addAttribute("username", person.getName());
            mv.addObject("user", person);
            modelMap.addAttribute("sysAdmin",  admin.getIdadmin());
            return mv;
        }
        /*否则, 为普通用户*/
        mv = new ModelAndView("/jsp/users/userHomePage");
        modelMap.addAttribute("username", person.getName());
        modelMap.addAttribute("user", person);
        logger.info("Checking if login user's authority IS normal user");
        return mv;

    }
    //todo: 显示调查问卷页面
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

    //todo:
    @RequestMapping("/survey/upload")
    @ResponseBody
    public Map<String, Object> processJSONSurvey(HttpServletResponse response,
                                          HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        logger.info("Process JSONSurvey in Controller");

        return map;
    }


    //验证手机短信是否发送成功
    //1;发送成功!;1;0;1;70;7440;
    //0;失败...
    @RequestMapping("register/sms")
    @ResponseBody
    public Map<String, Object> registerSms(HttpServletRequest request, HttpServletResponse response,
                                           ModelMap map,
                                           String vcode,
                                           String phone){
        Map<String, Object> resMap = new HashMap<>();
        logger.info("phone number:" + phone);
        logger.info("vcode to be sent to phone:" + vcode);

        /** 短信验证码存入session(session的默认失效时间30分钟) */
        map.addAttribute("vcode", vcode);

        String requestUrl = SmsBase.URL_SMS.replace("AIMCODES", phone);
        String res = SmsBase.httpRequest(requestUrl, "POST", null, vcode);

        logger.info("vcode sent to client: " + vcode);
        logger.info("http response: " + res);
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
                                  String vcode){
        logger.info("sessionVCode="+map.get("vcode"));
        logger.info("Actual vcode="+vcode);

        ModelAndView mv = new ModelAndView();
        Map<String, Object> resMap = new HashMap<>();
        // 获取session中存放的手机短信验证码
        String sessionVcode = (String) map.get("vcode");
        if (sessionVcode!=null && vcode!=null){
            if  (sessionVcode == vcode || sessionVcode.equalsIgnoreCase(vcode)) {
                // todo: 注册用户到数据库
                String id_code = (String) map.get("id");
                String name = (String) map.get("name");
                String phone = (String) map.get("phone");
                Person person = new Person();
                person.setID_code(PersonInfoUtils.md5(id_code.toLowerCase()));
                person.setName(name);
                person.setTel1(phone);
//                personService.addPerson(person);
                resMap.put("result", 1);
            }
            else
                resMap.put("result", 0);
        } else resMap.put("result", 0);
        return resMap;
    }

    @RequestMapping("/register")
    public ModelAndView register(String name, String ID_code){
        ModelAndView mv = new ModelAndView();
        Person person = personService.findPersonByID_code(name);
        //todo: 判别是否为单位管理员
        if (person != null){
            Integer idcenter = person.getIdcenter();

            if (idcenter != null){
                int cnt = centerService.findPersonInCentersByCenterid(idcenter);
                if (cnt > 0) {
                    logger.info(person.getName() + "为: 本地管理员管理员注册");
                    mv.setViewName("");
                    return mv;
                }
                else logger.info(person.getName() + "为：普通用户注册");
            }else logger.warn("注册用户为: 普通用户");
        }else{
            logger.warn("注册用户为普通用户");
        }
        //todo: 判断普通用户的身份证号是否在单位管理员提交的身份证号列表中
        mv.setViewName("views/success");
        return mv;
    }


    @RequestMapping("/logout")
    public String logout(@ModelAttribute("user") Person person,
                         SessionStatus sessionStatus){
        //sessionStatus中的setComplete方法可以将session中的内容全部清空
        sessionStatus.setComplete();
        //返回登陆页面
        return "views/auth/login";
    }
    //todo:注册
    @RequestMapping("/signupPage")
    public ModelAndView signUp(){
        ModelAndView mv = new ModelAndView("jsp/users/signup");
        return mv;
    }

    @RequestMapping("/user/preferences")
    public String preference(){
        return "views/errors/404";
    }

    @RequestMapping("/help/support")
    public String support(){
        return "views/errors/404";
    }

    /*404 Page Not Found*/
    @RequestMapping("*")
    public String _404PageNotFound(){
        return "views/errors/404";
    }
}
