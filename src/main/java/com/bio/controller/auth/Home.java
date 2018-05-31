package com.bio.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Home {
    @RequestMapping("/home")
    public String index(){
        return "../index";
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();

        return mv;
    }
    @RequestMapping("logout")
    public String logout(){
        return "";

    }
}
