package com.bio.controller.auth;

import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Person;
import com.bio.service.ICenterService;
import com.bio.service.IPersonService;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;

@Controller
@SessionAttributes({"user","username"})
public class Home {
    @Autowired
    IPersonService personService;
    @Autowired
    ICenterService centerService;

    @RequestMapping("/home")
    public String index(){
        return "../index";
    }

    @RequestMapping("login")
    public String loginPage(){
        return "views/auth/signIn";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ModelAndView login(@Param("ID_code") String ID_code,
                              @Param("name") String name,
                              ModelMap modelMap){
        ModelAndView mv = new ModelAndView();
        System.out.println(ID_code);
        System.out.println(name);
        System.out.println("----");
        System.out.println(PersonInfoUtils.md5(ID_code));

        Person person = personService.findPersonByID_code(PersonInfoUtils.md5(ID_code), name);
        System.out.println(person);
        //判断登陆用户是否为Admin user
        //关联关系查询 sn_in_center在表centers中存在
        Integer idcenter = person.getIdcenter();
        String pname = person.getName();

        //check 用户输入的name 是否与数据库中注册该身份证对应的姓名相同
        if (!pname.equals(name)){
            //test
            System.out.println("username not equal");
            mv.addObject("userNameError", "输入用户名错误");
            mv.setViewName("views/auth/signIn");
            return mv;
        }

        // 关联关系查询
        // 存在
        if (idcenter != null){
            System.out.println("inside association");
            int count = centerService.findPersonInCentersByCenterid(idcenter);
            System.out.println(count);
            if (count > 0) {// 说明其sn_in_center在centers表中存在
                //前往'index.jsp'页面
                modelMap.addAttribute("user", person);
                modelMap.addAttribute("username", person.getName());
                mv.setViewName("../index");
            } else { //说明其sn_in_center值不在centers表中，不是Admin user
                // 普通用户登陆
               mv.setViewName("views/success");
            }
        }else { // sn_in_center==null，说明不是Admin user
            //普通用户登陆
            mv.setViewName("views/success");
        }
        return mv;
    }
    @RequestMapping("/auth/logout")
    public String logout(@ModelAttribute("user") Person person,
                         SessionStatus sessionStatus){
        //sessionStatus中的setComplete方法可以将session中的内容全部清空
        sessionStatus.setComplete();

        //返回登陆页面
        return "views/auth/signIn";

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
}
