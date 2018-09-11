package com.bio.controller.admin;

import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UpdatePerson {

    @Autowired
    private IPersonService personService;
    private static Logger logger = Logger.getLogger(UpdatePerson.class);

    @RequestMapping(value = "/update")
    public ModelAndView update(@RequestParam("idperson") int idperson){
        logger.info(idperson);
        ModelAndView mv = new ModelAndView();

        Person person = personService.findPersonById(idperson);
        mv.addObject("person", person);

        logger.info(person);
        mv.setViewName("jsp/upload/updatePerson");
        return mv;
    }
    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    public ModelAndView updatePerson(HttpServletRequest request,
                                     Person person){
        ModelAndView mv = new ModelAndView();
        //test
        logger.info(person);
        personService.modifyPerson(person);
        mv.addObject("message", "updated user "+ person);
        mv.setViewName("/views/success");
        return mv;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam("idperson") int idperson){
        ModelAndView mv = new ModelAndView();
        Person person = personService.findPersonById(idperson);
        //test
        logger.info(person);
        mv.addObject("person", person);
        personService.removeById(idperson);
        mv.setViewName("views/success");
        return mv;
    }

}
