package com.bio.controller.auth;

import com.JsonGenerator.FetchData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.ClientInfoUtils;
import com.bio.Utils.InformedConsentUtil;
import com.bio.Utils.PersonInfoUtils;
import com.bio.Utils.SurveyUtil;
import com.bio.beans.*;
import com.bio.dao.IInformedConsentDao;
import com.bio.enums.ResultEnum;
import com.bio.exception.FlupException;
import com.bio.service.*;
import com.sms.SmsBase;
import com.wechat.utils.ScoreUtil;
import com.wechat.utils.SqlUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@SessionAttributes({"user", "username", "snAdmin", "wxuser", "sysAdmin", "vcode", "idcode", "centerNames", "idperson2", "firstValues", "q_version"})
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
    @Autowired
    IQtRiskModelService qtRiskModelService;
    @Autowired
    IRiskModelService riskModelService;
    @Autowired
    IInformedConsentDao iInformedConsentDao;
    @Autowired
    IJsonService jsonService;
    @Autowired
    ISurveyReportService reportService;

    @RequestMapping("/home")
    public ModelAndView index(ModelMap session) {
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

    @RequestMapping("returnHome")
    public void returnHomeCheck(ModelMap session, HttpServletResponse response) {
        try {
            if (session.get("snAdmin") != null) {
                logger.error("【returnHome】=单位管理员");
                response.sendRedirect("/snAdmin");
            } else {
                logger.error("【returnHome】=普通用户 ");
                response.sendRedirect("/userHomePage");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("snAdmin")
    public ModelAndView snAdminPage(ModelMap session) {
        ModelAndView mv = new ModelAndView("jsp/sys_admin/sys");
        return mv;
    }

    @RequestMapping("/userHomePage")
    public ModelAndView userHomePage(HttpServletResponse response,
                                     HttpServletRequest request,
                                     ModelMap session) {
        ModelAndView mv = new ModelAndView("jsp/users/userHomePage");
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
        if (idperson2 != null) {//代替亲属问答
            Person p2 = personService.findPersonByIdperson(idperson2);
            String gender2 = p2.getGender();
            sex = gender2.equals("男") ? 1 : 0;
        } else if (idperson1 != null) {//用户本人作答
            sex = personService.findPersonByIdperson(idperson1).getGender().equals("男") ? 1 : 0;
        }
        mv.addObject("gender", sex);
        if (idperson2 != null)
            session.addAttribute("idperson2", idperson2);
        return mv;
    }

    @RequestMapping("/bind/relative")
    public ModelAndView bindRelative(ModelMap session) {

        ModelAndView mv = new ModelAndView("jsp/users/BindRelatives");
        Person user = (Person) session.get("user");
        List<Relative> relatives = null;
        List<Person> persons = null;
        List<Integer> idpersons = null;

        if (user != null) {
            relatives = relativeService.findRelativesByIdperson1(user.getIdperson());
            idpersons = relatives.stream().map(Relative::getIdperson2).collect(Collectors.toList());
            persons = idpersons.stream().map(id -> personService.findPersonByIdperson(id)).collect(Collectors.toList());
        } else {

            relatives = relativeService.findRelativesByIdperson1(308);
            idpersons = relatives.stream().map(Relative::getIdperson2).collect(Collectors.toList());
            persons = idpersons.stream().map(id -> personService.findPersonByIdperson(id)).collect(Collectors.toList());
            mv.addObject("user", (Person) personService.findPersonByIdperson(308));
        }
        mv.addObject("persons", persons);
        return mv;
    }

    @RequestMapping("/unbind")
    public void unbindWxUser(HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap session) {

        Person user = (Person) session.get("user");

        weChatUserService.removeWxUserByIdperson(user.getIdperson());

        relativeService.removeRelativeByIdperson1(user.getIdperson());

        user.setTel1(null);

        personService.modifyPerson(user);

        try {
            response.sendRedirect("/signupPage");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(user);
        }
    }

    @RequestMapping("/addRelative")
    public String addRelative(@Param("ID_code") String ID_code,
                              @Param("name") String name,
                              @Param("relation") String relation,
                              ModelMap session,
                              Model model) {
        logger.info(ID_code);
        logger.info(name);
        logger.info(relation);
        Person toAdded = personService.findPersonByID_code(PersonInfoUtils.md5(ID_code));
        if (toAdded == null) {
            model.addAttribute("msg", "没有您的预申请信息，请联系专属管理员");
            return "redirect:/bind/relative";
        }
        Relative alreadyExist = (Relative) relativeService.findRelativesByIdperson2(toAdded.getIdperson());
        if (alreadyExist != null) {
            model.addAttribute("msg", "该亲属已绑定");
            return "redirect:/bind/relative";
        }
        Relative relative = new Relative();

        Person p = (Person) session.get("user");
        relative.setIdperson1(p != null ? p.getIdperson() : 1);
        relative.setIdperson2(toAdded.getIdperson());
        relative.setRelationship(Integer.valueOf(relation));
        relativeService.addRelative(relative);

        return "redirect:/bind/relative";
    }

    @RequestMapping("deleteRelative")
    public ModelAndView deleteRelative(HttpServletRequest request,
                                       ModelMap session,
                                       @RequestParam("idperson1") Integer idperson1,
                                       @RequestParam("idperson2") Integer idperson2) {

        ModelAndView mv = new ModelAndView("redirect:/bind/relative");
        logger.info(idperson1);
        logger.info(idperson2);
        relativeService.removeRelativeByIdperson1AndIdperson2(idperson1, idperson2);
        return mv;
    }


    @RequestMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("views/auth/login");
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public ModelAndView login(@Param("ID_code") String ID_code,
                              @Param("name") String name,
                              ModelMap session,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        /*根据身份证号和姓名, 获取person对象*/
        Person person = personService
                .findPersonByID_code(
                        PersonInfoUtils.md5(ID_code.toUpperCase())
                );
        if (person == null) {
            mv.setViewName("views/auth/login");
            mv.addObject("error", "请先完成注册!");
            logger.info("未注册用户");
            return mv;
        }

        LoginItem loginItem = new LoginItem();
        loginItem.setIdperson(person.getIdperson());
        loginItem.setIp(ClientInfoUtils.getIpAddr(request));
        loginItem.setTime(ClientInfoUtils.getCurrDatetime());
        loginService.addLoginItem(loginItem);

        logger.info(loginItem);
        session.addAttribute("user", person);
        mv.addObject("user", person);

        return loginAuthCheck(person.getIdperson(), mv, session);
    }

    public ModelAndView loginAuthCheck(int idperson, ModelAndView mv, ModelMap session) {

        Center center = centerService.findPersonInCentersByIdperson(idperson);
        Person person = personService.findPersonByIdperson(idperson);
        logger.info(center);
        logger.info(person.getID_code_cut());
        if (center != null && center.getIdperson() != null && center.getIdperson() == idperson) {
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
        logger.info(admin.getIdperson());
        if (admin != null && admin.getIdperson() == idperson) {
            mv.setViewName("/jsp/sys_admin/sys");
            mv.addObject("user", person);
            mv.addObject("username", person.getName());
            mv.addObject("sys_admin", "sys_admin");

            session.addAttribute("username", person.getName());
            session.addAttribute("user", person);
            session.addAttribute("sys_admin", "sys_admin");
            return mv;
        }
        mv.setViewName("/jsp/users/userHomePage");
        mv.addObject("username", person.getName());
        mv.addObject("idperson", person.getIdperson());
        mv.addObject("user", person);
        mv.addObject("nickname", person.getName());
        mv.addObject("msg", "参加人员界面");

        return mv;
    }

    @RequestMapping("/signupPage")
    public ModelAndView signUp() {
        return new ModelAndView("jsp/users/signupIdCode");
    }

    @RequestMapping("/signupPageFollowed")
    public ModelAndView signUpFollowed(HttpServletRequest request,
                                       String idcode,
                                       ModelMap session) {
        ModelAndView mv = new ModelAndView("jsp/users/signup");
        mv.addObject("idcode", idcode);
        Person p = personService.findPersonByID_code(PersonInfoUtils.md5(idcode));
        if (p != null) {
            mv.addObject("name", p.getName());
        }
        session.put("idcode", idcode);
        return mv;
    }

    @RequestMapping("register/idcheck")
    @ResponseBody
    public Map<String, Object> registerIdcodeCheck(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   String idcode,
                                                   ModelMap session) {
        Map<String, Object> resMap = new HashMap<>();
        logger.info("idcode=" + idcode);
        String md5 = PersonInfoUtils.md5(idcode.toUpperCase());

        Person p = personService.findPersonByID_code(md5);
        if (p == null) {
            logger.error("【待注册用户不在persons 表中】");
        } else {
            logger.info(p);
        }
        List<Person> persons = null;
        List<String> centerNames = null;
        Set<Integer> idcenters = null;
        if (p != null && p.getID_code().equalsIgnoreCase(md5)) {
            persons = personService.findAllPersons(PersonInfoUtils.md5(idcode));
            centerNames = new ArrayList<>();
            idcenters = persons.stream().map(Person::getIdcenter).collect(Collectors.toSet());

            logger.info(idcenters);
            for (int idcenter : idcenters) {
                Center center = centerService.findPersonInCentersByCenterid(idcenter);
                logger.info(center);
                centerNames.add(center.getIdcenter() + "_" + center.getCenter());
            }
            for (String str : centerNames)
                logger.info(str);

            resMap.put("result", "1");
            session.put("idcode", idcode);
            session.put("centerNames", centerNames);
        } else {
            p = new Person();
            List<Center> noCenters = centerService.findCentersBy1And2();
            if (noCenters == null) {
                logger.error("【noCenters】数组为空");
            }
            centerNames = noCenters.stream().map(Center::getCenter).collect(Collectors.toList());
            logger.info("【centerNames】=" + centerNames);
            session.put("centerNames", centerNames);
            session.put("idcode", idcode);
            resMap.put("result", "0");
        }

        return resMap;
    }

    //验证手机短信是否发送成功
    //1;发送成功!;1;0;1;70;7440;
    //0;失败...

    /**
     * @param request
     * @param response
     * @param httpSession
     * @param vcode
     * @param phone
     * @param idcode
     * @param centerName
     * @param type        1:表示预存人员 0:未预存人员
     * @return
     */
    @RequestMapping("register/sms")
    @ResponseBody
    public Map<String, Object> registerSms(HttpServletRequest request, HttpServletResponse response,
                                           HttpSession httpSession,
                                           ModelMap session,
                                           String vcode,
                                           String phone,
                                           String idcode,
                                           String centerName,
                                           String type,
                                           String name) {
        Map<String, Object> resMap = new HashMap<>();
        logger.info("接受验证码手机号=" + phone);
        logger.info("即将发送的验证码=" + vcode);
        logger.info("身份证号=" + idcode);
        logger.info("单位=" + centerName);
        logger.info("type=" + type);
        logger.info("姓名=" + name);
        /** 短信验证码存入session(session的默认失效时间30分钟) */
        httpSession.setAttribute("vcode", vcode);

        httpSession.setMaxInactiveInterval(2 * 60);

        Person p = new Person();
        if (type.equals("1")) {
            int idcenter = Integer.valueOf(centerName.substring(0, centerName.indexOf("_")));
            logger.info(idcenter);
            p = personService.findPersonByID_codeAndIdcenter(PersonInfoUtils
                    .md5(idcode.toUpperCase()), idcenter);

            if (p == null || p.getID_code() == null) {
                resMap.put("result", "-1");
                logger.error("注册用户身份证信息不在表person中");
                return resMap;
            }

            //手机号码为空
            if (p.getTel1() == null) {
                p.setTel1(phone);
                personService.modifyPerson(p);
                logger.error("数据库手机号码为空");
            }
            // 不为空 && 不匹配
            if ((p.getTel1() != null && !p.getTel1().equals(phone))) {
                resMap.put("result", -1);//返回"您的手机号与系统记录不符，请联系管理员核实"
                return resMap;
            }
        }

        //(手机号码不为空 && 匹配) || 手机号码为空
        //执行发送短信操作
        WeChatUser user = (WeChatUser) session.get("wxuser");
        WeChatUser user2 = (WeChatUser) httpSession.getAttribute("wxuser");

        //todo to be removed
        if (user == null) user = new WeChatUser();
        logger.info(user);
        logger.info(user2);


        if (user == null || user.getOpenid() == null || user.getOpenid().equals("")) {
            logger.warn("该用户不在Session!");
        } else {
            logger.info("该用户在Session.");
            logger.info("wxuser=" + JSONObject.toJSON(user));
        }

        String requestUrl = SmsBase.URL_SMS.replace("AIMCODES", phone);
        String res = SmsBase.httpRequest(requestUrl, "GET", null, vcode);
        logger.info("http=" + res);

        if (res != null && !res.equals("")) {
            if (res.startsWith("1")) {//success
                resMap.put("result", "1");
            } else if (res.startsWith("0")) {//failure
                resMap.put("result", "0");
            }
        } else resMap.put("result", "0");
        return resMap;
    }

    @RequestMapping("register/checkVcode")
    @ResponseBody
    public Map<String, Object> registerCheckVcode(HttpServletResponse response,
                                                  HttpServletRequest request,
                                                  String idcode,
                                                  HttpSession httpSession,
                                                  String vcode,
                                                  String centerName,
                                                  String phone,
                                                  String name,
                                                  String type) {
        logger.info("SessionVCode=" + httpSession.getAttribute("vcode"));
        logger.info("Input vcode=" + vcode);
        logger.info("idcode=" + idcode);
        logger.info("phone=" + phone);
        logger.info("centerName=" + centerName);
        logger.info("姓名=" + name);
        logger.info("type=" + type);

        Map<String, Object> resMap = new HashMap<>();
        String sessionVcode = (String) httpSession.getAttribute("vcode");

        if (sessionVcode != null && vcode != null && sessionVcode.equals(vcode)) {

            WeChatUser wxuser = (WeChatUser) httpSession.getAttribute("wxuser");
            if (wxuser != null) {
                logger.info(wxuser);
            } else {
                logger.error("【HttpSession不包含wxuser】");
            }

            if (type != null && !type.equals("1")) {
                Person p = new Person();

                Center center = centerService.findCenterByCenterName(centerName.substring(centerName.indexOf("_") + 1));
                if (center != null) {
                    logger.info(center);
                } else {
                    logger.error("【center查询不存在】, centerName=" + centerName.substring(centerName.indexOf("_") + 1));
                }

                int sn_in_center = personService.countPersonsByIdCenter(center.getIdcenter());
                p.setIdcenter(center.getIdcenter());
                p.setGender(PersonInfoUtils.getGender(idcode));
                p.setAge(PersonInfoUtils.getAge(idcode));
                p.setTel1(phone);
                p.setID_code(PersonInfoUtils.md5(idcode));
                p.setBirth(PersonInfoUtils.getBirth(idcode));
                p.setID_code_six(PersonInfoUtils.getIDCodeSix(idcode));
                p.setID_code_cut(PersonInfoUtils.getIDCodeCut(idcode));
                p.setName(name.substring(0, 1) + (p.getGender().equals("男") ? "先生" : "女士"));

                int num = personService.countPersonsByIdCenter(center.getIdcenter());

                String global_sn = center.getPostcode()
                        + "_"
                        + center.getLocal_num()
                        + "_"
                        + ++num;

                p.setGlobal_sn(global_sn);
                p.setSn_in_center("" + sn_in_center);
                personService.addPerson(p);
                p = personService.findPersonByID_code(PersonInfoUtils.md5(idcode));
                logger.info(p);
            }
            // 未预存 && 预存 都需要将微信用户信息写入wechat
            Person p = personService.findPersonByID_code(PersonInfoUtils.md5(idcode));
            if (p != null && p.getIdperson() != null) {
                wxuser.setIdperson(p.getIdperson());
            } else {
                logger.error("【用户数据出错】, " + p);
                resMap.put("result", 0);
                return resMap;
            }

            weChatUserService.addWxUser(wxuser);
            httpSession.setAttribute("wxuser", wxuser);
            httpSession.setAttribute("username", p.getName());
            httpSession.setAttribute("user", personService.findPersonByIdperson(wxuser.getIdperson()));

            resMap.put("result", 1);
        } else {
            resMap.put("result", 0);
        }
        return resMap;
    }

    @RequestMapping("/survey")
    public ModelAndView generateSurveyJSON(ModelMap session,
                                           @RequestParam(value = "gender", required = false) Integer gender) {
        ModelAndView mv = new ModelAndView();

        logger.info("正在调用调查问卷");
        logger.info(gender);
        mv.setViewName("jsp/questionaire/question");
        String surveyJSON = null;

        Person user = (Person) session.get("user");
        logger.info(user.getID_code_cut());

        Integer idperson = user != null ? user.getIdperson() : 1;

        Center c = centerService.findPersonInCentersByCenterid(user.getIdcenter());
        Integer sex = Integer.valueOf(gender);
        /*try {
            if (sex % 2 != 0)
                surveyJSON = FetchData.getSurveyJSON(c != null ? ((c.getCurrent_qtversion() != null) ? c.getCurrent_qtversion() : 1) : 1);
            else
                surveyJSON = FetchData.getSurveyJSON(c != null ? (c.getCurrent_qtversion() != null ? c.getCurrent_qtversion() + 1 : 1) : 1);
        } catch (JSchException e) {
            e.printStackTrace();
        }*/

        //todo: test
        if (sex % 2 != 0) {
            surveyJSON = jsonService.findJsonByVersion(c != null ? ((c.getCurrent_qtversion() != null) ? c.getCurrent_qtversion() : 1) : 1).getJson();
        } else {
            surveyJSON = jsonService.findJsonByVersion(c != null ? ((c.getCurrent_qtversion() != null) ? c.getCurrent_qtversion() + 1 : 1) : 1).getJson();
        }
        logger.info(surveyJSON);
        List<Integer> firstValues = FetchData.getFirstValues();

        logger.info(firstValues);

//        mv.addObject("firstValues", firstValues);
        mv.addObject("q_version", sex % 2 != 0 ? c.getCurrent_qtversion() : c.getCurrent_qtversion() + 1);
        mv.addObject("surveyJSON", surveyJSON);
        return mv;
    }

    @RequestMapping(value = "/process/survey", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> processSurvey(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody String surveyJson,
                                             ModelMap session
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            //https://blog.csdn.net/j080624/article/details/54598734
            logger.info(URLDecoder.decode(surveyJson, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int idquestionnaire = 0;
        JSONObject surveyJSON = JSON.parseObject(surveyJson);

        logger.info(session.get("user"));
        Person user = (Person) session.get("user");
        logger.info(user.getID_code_cut());

        Questionnaire questionnaire = null;
        logger.info(session.get("idperson2"));

        Integer version = (Integer) session.get("q_version");

        List<Qtnaireversion_riskmodel> riskmodelList = qtRiskModelService.findRiskModelByVersion(version);

        String lifetimeRisk = "";
        String fyrsRisk = "";
        String modelName = "";
        String filling_time = ClientInfoUtils.getCurrDatetime();


        if (surveyJSON != null) {

            questionnaire = new Questionnaire();

            questionnaire.setFilling_time(filling_time);
            if (session.get("idperson2") == null) {
                questionnaire.setIdperson(user != null ? user.getIdperson() : -1);
            } else {
                questionnaire.setIdperson((Integer) session.get("idperson2"));
            }

            questionnaire.setQtnaire_version(version != null ? version : -1);

            filling_time = questionnaire.getFilling_time();
            questionService.addQuestionAnswer(questionnaire);

            questionnaire = questionService.findQuestionByFillingTime(filling_time);

            logger.info(questionnaire);

        }

        for (Map.Entry<String, Object> item : surveyJSON.entrySet()) {
            logger.info(item.getKey());
            logger.info(item.getValue());

            if (questionnaire != null) {
                Answer answer = new Answer();
                answer.setIdquestion(Integer.parseInt(SurveyUtil.parseIdquestion(item)));
                answer.setAnswers(item.getValue().toString());
                answer.setIdperson(questionnaire.getIdperson());
                answer.setIdquestionnaire(questionnaire.getIdquestionnaire() != null ? questionnaire.getIdquestionnaire() : 1);
                logger.info(answer);
                answerService.addAnswer(answer);
            }
        }

        // fyrs_riskN
        List<Double> fyrsRiskList = new ArrayList<>();
        // lifetime_riskN
        List<Double> lifetimeRiskList = new ArrayList<>();

        List<String> missingModels = new ArrayList<>();

        try {

            Connection connection = FetchData.getRemoteConnection();
            Statement statement = connection.createStatement();

            for (Qtnaireversion_riskmodel qtnaireversionRiskmodel : riskmodelList) {

                lifetimeRisk = "";
                fyrsRisk = "";

                questionnaire = questionService.findQuestionByFillingTime(filling_time);
                logger.info(qtnaireversionRiskmodel);
                RiskModel rm = riskModelService.findRiskModelByIdriskmodel(qtnaireversionRiskmodel.getIdriskmodel());
                logger.info(rm);
                modelName = rm.getModelname();
                String sqlselectFactor = rm.getSqlselect_factor();

                String sqlselectRisk = rm.getSqlselect_risk();

                logger.info("【sqlselectRisk】=" + sqlselectRisk);
                logger.info("【sqlselectFactor】=" + sqlselectFactor
                        .replaceAll("IDPERSON", "" + user.getIdperson())
                        .replaceAll("IDQUESTIONNAIRE", "" + questionnaire.getIdquestionnaire()));

                // 10 factors
                ResultSet rs = statement.executeQuery(sqlselectFactor
                        .replaceAll("IDPERSON", "" + user.getIdperson())
                        .replaceAll("IDQUESTIONNAIRE", questionnaire.getIdquestionnaire() + ""));
                List<Integer> listValues = new ArrayList<>();

                while (rs.next()) listValues.add(rs.getInt(2));

                StringBuilder sqlBuilder = new StringBuilder();
                String[] strs = sqlselectRisk.split("\\?");

                if (strs != null && listValues != null) {

                    if (strs.length != listValues.size()) {
                        logger.error("【sqlselectRisk问号?数量与sqlselectFactor获取结果数量不一致】");
                        logger.error(strs.length + ";" + listValues.size());
                        throw new FlupException(ResultEnum.FACTOR_NOT_EQUAL);
                    } else {
                        logger.info("【数组大小一致】=" + strs.length);
                        for (int i = 0; i < strs.length; i++) sqlBuilder.append(strs[i]).append(listValues.get(i));

                        logger.info(sqlBuilder.toString());
                        ResultSet resultSet = statement.executeQuery(sqlBuilder.toString());

                        while (resultSet.next()) {
                            lifetimeRisk = resultSet.getString("lifetime_risk");
                            fyrsRisk = resultSet.getString("fyrs_risk");
                        }

                        logger.info("lifetime_risk=" + lifetimeRisk);
                        logger.info("5yrs_risk=" + fyrsRisk);

                        lifetimeRiskList.add(SqlUtil.stringToDouble(lifetimeRisk));
                        fyrsRiskList.add(SqlUtil.stringToDouble(fyrsRisk));

                        if (lifetimeRisk == null && fyrsRisk == null) {
                            map.putIfAbsent("missing", 1);
                            missingModels.add(rm.getModelname());
                            logger.info("【风险值缺失model】=" + rm.getCancername());
                        }
                    }
                }

                // questionService.modifyQuestionnaire(questionnaire);
                // dynamic update risk model values;
                String updateSql = "update questionnaire set risk_"
                        + modelName
                        + "='"
                        + SqlUtil.riskModelValue(lifetimeRisk, fyrsRisk)
                        + "' where idquestionnaire="
                        + questionnaire.getIdquestionnaire();
                logger.warn(updateSql);
                statement.executeUpdate(updateSql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        questionnaire = questionService.findQuestionByFillingTime(filling_time);
        idquestionnaire = questionnaire.getIdquestionnaire();
//        List<Integer> firstValues = (List<Integer>) session.get("firstValues");

//        logger.info(firstValues);

//        int count = SqlUtil.countDuplicateQuestions(firstValues, answerService, idquestionnaire);
        int count = 1111;
        logger.info("count=" + count);

        // 计算lifetime_risk, fyrs_risk
        double lfr = SqlUtil.countRisk(lifetimeRiskList), fyr = SqlUtil.countRisk(fyrsRiskList);
        logger.info("lfr=" + lfr);
        logger.info("fyr=" + fyr);

        questionnaire.setScore(count);
        questionnaire.setLifetime_risk(String.valueOf(lfr));
        questionnaire.setFyrs_risk(String.valueOf(fyr));

        // 获取lifetime_risk/fyrs_risk min/max
        Qtnaireversion_riskmodel qtnaireversion_riskmodel =
                qtRiskModelService.findRiskModelByVersionLimitOne(questionnaire.getQtnaire_version());

        logger.info("【模型】=" + qtnaireversion_riskmodel);

        double lifetime_risk_score = ScoreUtil.lifetime_risk_score(
                ScoreUtil.scoreEmptyNull(SqlUtil.stringEmptyNull(qtnaireversion_riskmodel.getLifetime_min())),
                ScoreUtil.scoreEmptyNull(SqlUtil.stringEmptyNull(qtnaireversion_riskmodel.getLifetime_max())),
                lfr
        );
        double fyrs_risk_score = ScoreUtil.fyrs_risk_score(
                ScoreUtil.scoreEmptyNull(SqlUtil.stringEmptyNull(qtnaireversion_riskmodel.getFyrs_min())),
                ScoreUtil.scoreEmptyNull(SqlUtil.stringEmptyNull(qtnaireversion_riskmodel.getFyrs_max())),
                fyr
        );

        double low_risk = ScoreUtil.getRisk(qtnaireversion_riskmodel.getLow_risk());
        double high_risk = ScoreUtil.getRisk(qtnaireversion_riskmodel.getHigh_risk());
        logger.info("lifetime_risk_score=" + lifetime_risk_score);
        logger.info("fyrs_risk_score=" + fyrs_risk_score);
        logger.info("low_risk=" + low_risk);
        logger.info("high_risk=" + high_risk);

        questionnaire.setLifetime_score(String.valueOf(lifetime_risk_score));
        questionnaire.setFyrs_score(String.valueOf(fyrs_risk_score));

        logger.info(questionnaire);
        questionService.modifyQuestionnaire(questionnaire);
        logger.info(count);
        //记录问卷调查总分
        map.put("count", count);
        map.put("fyrs_score", fyrs_risk_score);
        map.put("lifetime_score", lifetime_risk_score);
        map.put("riskEvaluation", ScoreUtil.evaluateRisk(low_risk, high_risk, lifetime_risk_score));

        map.put("lifetime_risk", SqlUtil.riskModelValue(lifetimeRisk, fyrsRisk));
        if (map.get("missing") != null) {
            map.put("missingModels", missingModels);
        }
        //todo save SurveyReport to db and cache idquestionnaire for retrieval
        reportService.addSurveyReport(idquestionnaire,
                String.valueOf(low_risk),
                String.valueOf(high_risk),
                String.valueOf(fyrs_risk_score),
                String.valueOf(lifetime_risk_score),
                count,
                String.join(";", missingModels));
        map.put("idquestionnaire", idquestionnaire);
        return map;
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus,
                         HttpSession httpSession) {
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

        //返回home
        return "../index";
    }

    @RequestMapping("/informedConsent/acknowledge")
    public String informedConsent(HttpServletRequest request,
                                  ModelMap modelMap,
                                  @RequestParam(value = "idperson2", required = false) Integer idperson2,
                                  @RequestParam(value = "idperson1", required = false) Integer idperson1
    ) {
        InformedConsent informedConsent = iInformedConsentDao.selectInformedConsent();
        if (informedConsent == null) {
            logger.error("【提取认证申请书错误】");
        }
        String html = informedConsent.getInformed_consent1();
        String style = InformedConsentUtil.getStyle(html);
        String body = InformedConsentUtil.getBody(html);
        modelMap.addAttribute("style", style);
        modelMap.addAttribute("body", body);

        Integer sex = 0;
        logger.info(idperson1);
        logger.info(idperson2);
        if (idperson2 != null) {//代替亲属问答
            Person p2 = personService.findPersonByIdperson(idperson2);
            String gender2 = p2.getGender();
            sex = gender2.equals("男") ? 1 : 0;
        } else if (idperson1 != null) {//用户本人作答
            sex = personService.findPersonByIdperson(idperson1).getGender().equals("男") ? 1 : 0;
        }
        modelMap.addAttribute("gender", sex);
        if (idperson2 != null)
            modelMap.addAttribute("idperson2", idperson2);
        logger.info("【/user/informedConsent】进入");
        return "jsp/users/informedConsent";
    }

    @RequestMapping("/success")
    public String success(Model model) {
        return "views/success";
    }

    /*404 Page Not Found*/
    @RequestMapping("*")
    public String _404PageNotFound(HttpServletRequest request) {
        return "views/errors/404";
    }

    @RequestMapping("/survey/result/display/{idquestionnaire}")
    public String surveyReportDisplay(@PathVariable("idquestionnaire") Integer idquestionnaire,
                                      ModelMap modelMap) {

        SurveyReport report = reportService.findSurveyReportByIdquestionnaire(idquestionnaire);
        modelMap.addAttribute("fyrs_score", report.getFyrs_score());
        modelMap.addAttribute("lifetime_score", report.getFyrs_score());
        modelMap.addAttribute("count", report.getQuestionnaire_score());
        modelMap.addAttribute("modelnames", report.getModelnames());
        modelMap.addAttribute("evaluation", ScoreUtil.evaluateRisk(
                Double.valueOf(report.getLow_risk()),
                Double.valueOf(report.getHigh_risk()),
                Double.valueOf(report.getLifetime_score())));

        return "jsp/questionaire/report";
    }
}
