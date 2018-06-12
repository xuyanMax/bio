package com.bio.controller.auth;

import com.bio.Utils.ClientInfoUtils;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Admin;
import com.bio.beans.LoginItem;
import com.bio.beans.Person;
import com.bio.service.IAdminService;
import com.bio.service.ICenterService;
import com.bio.service.ILoginService;
import com.bio.service.IPersonService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@SessionAttributes({"user","username", "snAdmin", "sysAdmin"})/*单位管理员，系统管理员*/
public class Home {
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
                              HttpSession session,
                              HttpServletRequest request){
        ModelAndView mv = null;
        /*测试*/
        System.out.println(ID_code);
        System.out.println(name);
        System.out.println("----");
        System.out.println(PersonInfoUtils.md5(ID_code));
        /*测试结束*/

        /*根据身份证号, 获取person对象*/
        Person person = personService.findPersonByID_code(PersonInfoUtils.md5(ID_code), name);

        /*组装login item*/
        LoginItem loginItem = new LoginItem();
        loginItem.setIdperson(person.getIdperson());
        loginItem.setIp(ClientInfoUtils.getIpAddr(request));
        loginItem.setTime(ClientInfoUtils.getCurrDatetime());
        loginItem.setSup2(person.getSup2()==null?"":person.getSup2());
        loginItem.setSup3(person.getSup3()==null?"":person.getSup3());
        loginService.addLoginItem(loginItem);
        /*组装结束*/

        if (person == null){
            mv = new ModelAndView("views/auth/login");
            mv.addObject("error", "未注册用户!!!");
            return mv;
        }
        /*测试*/
        System.out.println(person);
        /*测试结束*/

        /*判断登陆用户是否为单位管理员*/
        /*关联关系查询 sn_in_center在表centers中存在*/
        Integer idcenter = person.getIdcenter();
        String pname = person.getName();

        /*测试*/
        System.out.println(idcenter);
        System.out.println(pname);
        /*测试结束*/

        /*输入检验1: 用户输入的name是否与数据库中注册该身份证对应的姓名相同*/
        if (!pname.equals(name)){
            /*测试*/
            System.out.println("aa");
            System.out.println("username not equal");
            /*测试结束*/
            mv = new ModelAndView("forward:views/auth/login");
            mv.addObject("error", "输入用户名错误!!!");
            return mv;
        }

         /*关联关系查询*/
        if (idcenter != null){
            /*测试*/
            System.out.println("在关联内");
            int cnt = centerService.findPersonInCentersByCenterid(idcenter);
            System.out.println(cnt);
            /*测试结束*/
            if (cnt > 0) {// 说明其sn_in_center在centers表中存在
                /*添加session attribute*/
//                modelMap.addAttribute("user", person);
                modelMap.addAttribute("username", person.getName());
                mv.addObject("user", person);
                modelMap.addAttribute("snAdmin", "snAdmin");
                //前往主页面
                mv = new ModelAndView("../index");
                return mv;
            } /*else { //说明其sn_in_center值不在centers表中，不是单位管理员
                     //有可能是系统管理员
                mv = new ModelAndView();
                //暂定
                mv.addObject("admin-error", "不是管理员账户");
               mv.setViewName("views/auth/login");
               return mv;
            }*/
        }/*else { // sn_in_center==null，说明不是Admin user
            //普通用户登陆
            //todo: redirect 代替 forward 实现url变化
            mv = new ModelAndView("forward:/views/success");
            return mv;
        }*/
        /*判断用户是否为系统管理员*/
        //关联关系查询 idperson是否在表admin中存在
        Admin admin  = adminService.selectAdminUser(person.getIdperson());
        if (admin !=null && admin.getIdperson() == person.getIdperson()){
            mv = new ModelAndView("/jsp/sys_admin/sys");
            modelMap.addAttribute("username", person.getName());
            mv.addObject("user", person);
            modelMap.addAttribute("sysAdmin",  admin.getIdadmin());
            return mv;
        }
        /*否则为普通用户*/
        mv = new ModelAndView("/jsp/users/userHomePage");
        modelMap.addAttribute("username", person.getName());
        modelMap.addAttribute("user", person);
        return mv;

    }
    @RequestMapping("/auth/logout")
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
        return "";
    }

    @RequestMapping("/help/support")
    public String support(){
        return "";
    }

    //todo: 404
    /*404 Page Not Found*/
    @RequestMapping("*")
    public String _404NotFound(){
        return "views/errors/404";
    }
}
