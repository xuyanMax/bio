package com.bio.controller.auth;

import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Person;
import com.bio.service.ICenterService;
import com.bio.service.IPersonService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


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

    @RequestMapping("/login")
    public String loginPage(){
        return "views/auth/login";
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public ModelAndView login(@Param("ID_code") String ID_code,
                              @Param("name") String name,
                              ModelMap modelMap,
                              HttpSession session){
        ModelAndView mv = null;

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
            System.out.println("aa");
            System.out.println("username not equal");
            mv = new ModelAndView("forward:views/auth/login");
            mv.addObject("userNameError", "输入用户名错误");
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
                mv = new ModelAndView("../index");

                System.out.println(mv.getViewName());

                System.out.println("aac");
                return mv;
            } else { //说明其sn_in_center值不在centers表中，不是Admin user
                // 普通用户登陆
                mv = new ModelAndView("forward:/views/success");
//               mv.setViewName("redirect:/views/success");
               return mv;
            }
        }else { // sn_in_center==null，说明不是Admin user
            //普通用户登陆
            //todo: redirect 代替 forward 实现url变化
            mv = new ModelAndView("forward:/views/success");
            return mv;
        }
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
}
