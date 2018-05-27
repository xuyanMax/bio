package com.bio.controller.admin;

import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class updatePerson {

    @Autowired
    private IPersonService personService;
    @RequestMapping(value = "/update")
    public ModelAndView update(@RequestParam("idperson") int idperson){
        System.out.println(idperson);
        ModelAndView mv = new ModelAndView();
        Person person = personService.findPersonById(idperson);
        mv.addObject("person", person);

        //todo: updateUser.jsp page
        System.out.println(person);
        mv.setViewName("views/success");
        return mv;
    }
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("idperson") int idperson){
        System.out.println(idperson);
        personService.removeById(idperson);

        return "views/success";
    }
    @RequestMapping("/displayUsers")
    public ModelAndView displayAllUsers(){
        ModelAndView mv = new ModelAndView();
        List<Person> personList = personService.findAllPersons();
        mv.addObject("persons", personList);

        //测试
        System.out.println(personList);

        mv.setViewName("views/personList");
        return mv;
    }

}
