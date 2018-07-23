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


@Controller
@SessionAttributes({"user","username", "snAdmin", "sysAdmin"})/*单位管理员，系统管理员*/
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
    public String index(){
        return "../index";
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
        /*测试*/
        System.out.println(ID_code);
        System.out.println(name);
        System.out.println("----");
        System.out.println(PersonInfoUtils.md5(ID_code));
        /*测试结束*/

        /*根据身份证号和姓名, 获取person对象*/
        Person person = personService.findPersonByID_code(PersonInfoUtils.md5(ID_code), name);

        if (person == null){
            mv = new ModelAndView("views/auth/login");
            mv.addObject("error", "非合法用户!!!");
            /*logger*/
            logger.info("非法用户尝试登陆!");
            return mv;
        }
        /*测试*/
        System.out.println(person);
        /*测试结束*/

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

        /*测试*/
        System.out.println("idcenter: " + idcenter);
        System.out.println("name " + pname);
        /*测试结束*/

         /*关联关系查询*/
        if (idcenter != null){
            /*测试*/
            System.out.println("在关联内");
            //通过idcenter判断，该用户是否在centers表中，在则是单位管理员，否则不是单位管理员，可能为普通用户或系统管理员
            int cnt = centerService.findPersonInCentersByCenterid(idcenter);
            System.out.println("cnt = " + cnt);
            /*测试结束*/
            // todo: 本单位内查重??
            if (cnt > 0) {// 说明其sn_in_center在centers表中存在

                mv = new ModelAndView("redirect:/home");
                mv.addObject("user", person);
                mv.addObject("username", person.getName());
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
        return mv;

    }
    //todo: 显示调查问卷页面
    @RequestMapping("/user/survey")
    public ModelAndView generateSurveyJSON(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/jsp/questionaire/question");
        String surveyJSON = null;
        try {
            surveyJSON = FetchData.getSurveyJSON();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        mv.addObject("surveyJSON", surveyJSON);
        return mv;
    }


    @RequestMapping("user/surveyUpload")
    public ModelAndView surveyReceive(String surveyJSON){
        ModelAndView mv = new ModelAndView();

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
    @RequestMapping("/signup")
    public ModelAndView signUp(){
        ModelAndView mv = new ModelAndView();
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
