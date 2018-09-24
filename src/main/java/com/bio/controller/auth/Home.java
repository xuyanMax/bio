package com.bio.controller.auth;

import com.JsonGenerator.FetchData;
import com.alibaba.fastjson.JSON;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@SessionAttributes({"user","username", "snAdmin", "wxuser", "sysAdmin", "vcode", "idcode", "centerNames", "idperson2"})
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
    @Autowired
    IAnswerService answerService;
    @Autowired
    IQuestionService questionService;
    @Autowired
    IRelativeService relativeService;

    @RequestMapping("/home")
    public ModelAndView index(ModelMap session){
        ModelAndView mv = new ModelAndView("../index");
        if (session.get("username") != null) {
            mv.addObject("username", session.get("username"));
            logger.info(session.get("username"));
        }
        if (session.get("snAdmin") != null) {
            mv.addObject("snAdmin", session.get("snAdmin"));
            logger.info(session.get("snAdmin"));
        }
        return mv;
    }
    @RequestMapping("snAdmin")
    public ModelAndView snAdminPage(ModelMap session){
        ModelAndView mv = new ModelAndView("jsp/sys_admin/sys");

        return mv;
    }

    @RequestMapping("/userHomePage")
    public ModelAndView userHomePage(HttpServletResponse response,
                                     HttpServletRequest request,
                                     ModelMap session){
        ModelAndView mv = new ModelAndView("jsp/users/userHomePage");
        //todo:
        mv.addObject("idperson", request.getAttribute("idperson"));
        return mv;
    }
    @RequestMapping("informant")
    public ModelAndView signInformant(@RequestParam(value = "idperson2", required = false) Integer idperson2,
                                      @RequestParam(value = "idperson1", required = false) Integer idperson1,
                                      ModelMap session) {
        ModelAndView mv = new ModelAndView("jsp/questionaire/informant");
        Integer sex = 0;
        logger.info(idperson1);
        logger.info(idperson2);
        if (idperson2 != null){//代替亲属问答
            Person p2 = personService.findPersonByIdperson(idperson2);
            String gender2 = p2.getGender();
            sex = gender2.equals("男")?1:0;
        }else if (idperson1 != null){//用户本人作答
         sex = personService.findPersonByIdperson(idperson1).getGender().equals("男")?1:0;
        }
        mv.addObject("gender", sex);
        if (idperson2 != null)
            session.addAttribute("idperson2", idperson2);
        return mv;
    }

    @RequestMapping("/bind/relative")
    public ModelAndView bindRelative(ModelMap session){

        ModelAndView mv = new ModelAndView("jsp/users/BindRelatives");
        Person user = (Person) session.get("user");
        List<Relative> relatives = null;
        List<Person> persons = null;
        List<Integer> idpersons = null;
        //todo:处理user==null
        if (user != null){
            relatives = relativeService.findRelativesByIdperson1(user.getIdperson());
            logger.info(relatives);
            idpersons = relatives.stream().map(Relative::getIdperson2).collect(Collectors.toList());
            persons = idpersons.stream().map(id->personService.findPersonByIdperson(id)).collect(Collectors.toList());
            logger.info(persons);
        } else {
            //todo:仅用于测试
            relatives = relativeService.findRelativesByIdperson1(308);
            logger.info(relatives);
            idpersons = relatives.stream().map(Relative::getIdperson2).collect(Collectors.toList());
            persons = idpersons.stream().map(id->personService.findPersonByIdperson(id)).collect(Collectors.toList());

            mv.addObject("user", (Person)personService.findPersonByIdperson(308));
            logger.info(persons);
        }
        mv.addObject("persons", persons);
        return mv;
    }
    @RequestMapping("/unbind")
    public void unbindWxUser(HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelMap session){
        Person user = (Person) session.get("user");
        weChatUserService.removeWxUserByIdperson(user.getIdperson());
        try {
            response.sendRedirect("/wx/login");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(user);
        }
    }

    @RequestMapping("addRelative")
    public ModelAndView addRelative(@Param("ID_code") String ID_code,
                                    @Param("name") String name,
                                    @Param("relation") String relation,
                                    ModelMap session){
        logger.info(ID_code);
        logger.info(name);
        logger.info(relation);
        ModelAndView mv = new ModelAndView("jsp/users/BindRelatives");
        Person toAdded = personService.findPersonByID_code(PersonInfoUtils.md5(ID_code));
        if (toAdded == null){
            mv.addObject("msg", "没有您的预申请信息，请联系专属管理员");
            return mv;
        }
        Relative alreadyExist = (Relative) relativeService.findRelativesByIdperson2(toAdded.getIdperson());
        if (alreadyExist != null) {
            mv.addObject("msg", "该亲属已绑定");
            return mv;
        }
        Relative relative = new Relative();

        Person p = (Person) session.get("user");
        logger.info(p);
        relative.setIdperson1(p!=null?p.getIdperson():1);
        relative.setIdperson2(toAdded.getIdperson());
        relative.setRelationship(Integer.valueOf(relation));

        logger.info(relative);
        relativeService.addRelative(relative);
        mv.setViewName("jsp/users/BindRelatives");

        return mv;
    }
    @RequestMapping("deleteRelative")
    public ModelAndView deleteRelative(HttpServletRequest request,
                                       ModelMap session,
                                       @RequestParam("idperson1") Integer idperson1,
                                       @RequestParam("idperson2") Integer idperson2){

        ModelAndView mv = new ModelAndView("jsp/users/BindRelatives");
        logger.info(idperson1);
        logger.info(idperson2);
        relativeService.removeRelativeByIdperson1AndIdperson2(idperson1, idperson2);
        return mv;
    }


    @RequestMapping("/login")
    public String loginPage(){
        return "views/auth/login";
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public ModelAndView login(@Param("ID_code") String ID_code,
                              @Param("name") String name,
                              ModelMap session,
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

        return loginAuthCheck(person.getIdperson(), mv, session);
    }

    public ModelAndView loginAuthCheck(int idperson, ModelAndView mv, ModelMap session){

        Center center = centerService.findPersonInCentersByIdperson(idperson);
        Person person = personService.findPersonByIdperson(idperson);
        logger.info(center);
        logger.info(person);
        if (center != null && center.getIdperson() != null && center.getIdperson() == idperson){
            mv.addObject("username", person.getName());
            mv.addObject("user", person);
            mv.addObject("snAdmin", "snAdmin");

            session.put("username", person.getName());
            session.put("snAdmin", "syAdmin");
            session.put("user", person);
            mv.setViewName("jsp/sys_admin/sys");
            return mv;
        }

        Admin admin = adminService.findAdminUser(idperson);
        logger.info(admin);
        if (admin != null && admin.getIdperson() == idperson){
            mv.setViewName("/jsp/sys_admin/sys");
            mv.addObject("user", person);
            mv.addObject("username", person.getName());
            mv.addObject("sys_admin", "sys_admin");

            session.addAttribute("username", person.getName());
            session.addAttribute("user", person);
            session.addAttribute("sys_admin", "sys_admin");
            return mv;
        }
        //todo: 参加人员界面
        mv.setViewName("/jsp/users/userHomePage");
        mv.addObject("username", person.getName());
        mv.addObject("idperson", person.getIdperson());
        mv.addObject("user", person);
        mv.addObject("nickname", person.getName());
        mv.addObject("msg", "参加人员界面");

        return mv;
    }

    @RequestMapping("/signupPage")
    public ModelAndView signUp(){
        return new ModelAndView("jsp/users/signupIdCode");
    }

    @RequestMapping("/signupPageFollowed")
    public ModelAndView signUpFollowed(HttpServletRequest request,
                                       String idcode,
                                       ModelMap session){
        ModelAndView mv = new ModelAndView("jsp/users/signup");
        mv.addObject("idcode", idcode);
        session.put("idcode", idcode);
        return mv;
    }
    @RequestMapping("register/idcheck")
    @ResponseBody
    public Map<String, Object> registerIdcodeCheck(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   String idcode,
                                                   ModelMap session){
        Map<String, Object> resMap = new HashMap<>();
        logger.info("idcode="+idcode);
        String md5 = PersonInfoUtils.md5(idcode.toUpperCase());

        Person p = personService.findPersonByID_code(md5);
        logger.info(p);
        List<Person> persons = null;
        List<String> centerNames = null;
        List<Integer> idcenters = null;
        if (p != null && p.getID_code().equalsIgnoreCase(md5)) {
             persons = personService.findAllPersons(PersonInfoUtils.md5(idcode));
             centerNames = new ArrayList<>();
             idcenters = persons.stream().map(Person::getIdcenter).collect(Collectors.toList());

            logger.info(idcenters);
            for (int idcenter : idcenters){
                Center center = centerService.findPersonInCentersByCenterid(idcenter);
                logger.info(center);
                centerNames.add(center.getIdcenter() + "_" + center.getCenter());
            }
            for(String str:centerNames)
                logger.info(str);

            resMap.put("result", "1");
            session.put("idcode", idcode);
            logger.info("ok");
            session.put("centerNames", centerNames);
        }else resMap.put("result", "0");

        return resMap;
    }
    //验证手机短信是否发送成功
    //1;发送成功!;1;0;1;70;7440;
    //0;失败...
    @RequestMapping("register/sms")
    @ResponseBody
    public Map<String, Object> registerSms(HttpServletRequest request, HttpServletResponse response,
                                           ModelMap session,
                                           String vcode,
                                           String phone,
                                           String idcode,
                                           String centerName){
        Map<String, Object> resMap = new HashMap<>();
        logger.info("接受验证码手机号=" + phone);
        logger.info("即将发送的验证码=" + vcode);
        logger.info("身份证号=" + idcode);
        logger.info("单位="+centerName);

        /** 短信验证码存入session(session的默认失效时间30分钟) */
        session.addAttribute("vcode", vcode);
        //测试是ok的
        //todo 通过id_code && idcenter

        int idcenter = Integer.valueOf(centerName.substring(0, centerName.indexOf("_")));
        logger.info(idcenter);
        Person p = personService.findPersonByID_codeAndIdcenter(PersonInfoUtils
                        .md5(idcode.toUpperCase()), idcenter);
        logger.info(p);

        if (p == null || p.getID_code() == null){
            resMap.put("result", "-1");
            logger.error("注册用户身份证信息不在表person中");
            return resMap;
        }

        if (p.getTel1() == null || (p.getTel1() != null && !p.getTel1().equals(phone)) ){
            resMap.put("result", "1");
            logger.error("单位管理员，手机号码不匹配");
            p.setTel1(phone);
            personService.modifyPerson(p);
//            return resMap;
        }
        WeChatUser user = (WeChatUser) session.get("wxuser");

        user.setIdperson(p.getIdperson());

        //todo: 添加openid, unionid处理
        if (user == null || user.getOpenid() == null || user.getOpenid().equals("")) {
            logger.warn("该用户不在Session!!");
        }else {
            logger.info("该用户在Session." );
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
                                  ModelMap session,
                                  String ID_code,
                                  String vcode){
        logger.info("sessionVCode="+session.get("vcode"));
        logger.info("Actual vcode="+vcode);
        logger.info("idcode="+ID_code);

        ModelAndView mv = new ModelAndView();

        Map<String, Object> resMap = new HashMap<>();
        String sessionVcode = (String) session.get("vcode");

        if (sessionVcode!=null && vcode!=null && sessionVcode.equals(vcode)){

                WeChatUser user = (WeChatUser) session.get("wxuser");
                logger.info(user);

                weChatUserService.addWxUser(user);

                resMap.put("result", 1);
        } else {
            resMap.put("result", 0);
            logger.info("");
        }
        return resMap;
    }

    @RequestMapping("/survey")
    public ModelAndView generateSurveyJSON(ModelMap session,
                                           @RequestParam(value = "gender", required = false) Integer gender){
        ModelAndView mv = new ModelAndView();

        logger.info("正在调用调查问卷");
        logger.info(gender);
        mv.setViewName("jsp/questionaire/question");
        String surveyJSON = null;

        Person user = (Person) session.get("user");
        logger.info(user);

        Integer idperson = user!=null?user.getIdperson():1;

        Center c = centerService.findPersonInCentersByIdperson(idperson);
        Integer sex = Integer.valueOf(gender);
        try {
            if (sex % 2 != 0)
                surveyJSON = FetchData.getSurveyJSON(c!=null?((c.getCurrent_qtversion()!=null)?c.getCurrent_qtversion():1):1);
            else
                surveyJSON = FetchData.getSurveyJSON(c!=null?(c.getCurrent_qtversion()!=null?c.getCurrent_qtversion()+1:1):1);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        mv.addObject("surveyJSON", surveyJSON);
        return mv;
    }

    @RequestMapping(value = "/process/survey", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> processSurvey(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody String surveyJson,
                                             ModelMap session
                                             ){
        Map<String, Object> map = new HashMap<>();
        try {
            //https://blog.csdn.net/j080624/article/details/54598734
            logger.info(URLDecoder.decode(surveyJson, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject surveyJSON = JSON.parseObject(surveyJson);

        logger.info(session.get("user"));
        Person user = (Person) session.get("user");
        logger.info(user);
        logger.info(surveyJSON.getString("1"));

        Questionnaire questionnaire = null;
        logger.info(session.get("idperson2"));
        if (surveyJSON != null) {
            questionnaire = new Questionnaire();

            questionnaire.setFilling_time(ClientInfoUtils.getCurrDatetime());
            if (session.get("idperson2") == null)
                questionnaire.setIdperson(user != null ? user.getIdperson() : 1);
            else
                questionnaire.setIdperson((Integer) session.get("idperson2"));
            //todo 临时
            Integer version = (Integer) session.get("q_version");
            questionnaire.setQtnaire_version(version != null ? version : 1);

            questionnaire.setScore(0);

            logger.info(questionnaire);

            String filling_time = questionnaire.getFilling_time();
            questionService.addQuestionAnswer(questionnaire);

            questionnaire = questionService.findQuestionByFillingTime(filling_time);

            logger.info(questionnaire);
        }

        for (Map.Entry<String, Object> item:surveyJSON.entrySet()){
            logger.info(item.getKey());
            logger.info(item.getValue());

            if (questionnaire != null){
                Answer answer = new Answer();
                answer.setIdquestion(Integer.valueOf(item.getKey()));
                answer.setAnswers(item.getValue().toString());
                answer.setIdperson(questionnaire.getIdperson());
                //todo
                answer.setIdquestionnaire(questionnaire.getIdquestionnaire()!=null?questionnaire.getIdquestionnaire():1);
                logger.info(answer);
                answerService.addAnswer(answer);
            }

        }
        return map;
    }

    @RequestMapping("/logout")
    public String logout( SessionStatus sessionStatus,
                          HttpSession httpSession){
        //sessionStatus中的setComplete方法可以将session中的内容全部清空
        logger.info("logout");
        httpSession.removeAttribute("user");
        httpSession.removeAttribute("wxuser");
        httpSession.removeAttribute("snAdmin");
        httpSession.removeAttribute("username");
        httpSession.removeAttribute("vcode");
        httpSession.removeAttribute("idcode");
        httpSession.removeAttribute("centerNames");
        httpSession.removeAttribute("sysAdmin");
        httpSession.removeAttribute("idperson2");
        sessionStatus.setComplete();

        //返回登陆页面
        return "views/auth/login";
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
        return "views/errors/404";
    }

    @RequestMapping("/testInsertAnswer")
    public String testInsertAnswer(){

        JSONObject surveyJSON = JSON.parseObject("{\"1\":\"asdasd\",\"3\":\"1\",\"4\":\"190\",\"5\":\"100\",\"35\":\"3\",\"67\":[\"2\",\"4\"],\"89\":[{\"关系\":\"同父母的兄弟姐妹\"}],\"91\":\"0\"}");
        Person user = new Person();
        user.setIdperson(2);
        Questionnaire questionnaire = new Questionnaire();

        questionnaire.setFilling_time(ClientInfoUtils.getCurrDatetime());
        questionnaire.setIdperson(user!=null?user.getIdperson():1);
        //todo 临时
        Center c = centerService.findPersonInCentersByIdperson(user.getIdperson());
        questionnaire.setQtnaire_version(c.getIdcenter());
        questionnaire.setScore(0);

        logger.info(questionnaire);

        String filling_time = questionnaire.getFilling_time();
        questionService.addQuestionAnswer(questionnaire);

        questionnaire = questionService.findQuestionByFillingTime(filling_time);

        logger.info(questionnaire);

        for (Map.Entry<String, Object> item:surveyJSON.entrySet()){
            logger.info(item.getKey());
            logger.info(item.getValue());

            if (questionnaire != null){
                Answer answer = new Answer();
                answer.setIdquestion(Integer.valueOf(item.getKey()));
                answer.setAnswers(item.getValue().toString());
                answer.setIdperson(questionnaire.getIdperson());
                //todo
                answer.setIdquestionnaire(questionnaire.getIdquestionnaire()!=null?questionnaire.getIdquestionnaire():1);
                logger.info(answer);
                answerService.addAnswer(answer);
            }

        }
        return "../index";
    }


}
