package com.bio.controller.admin;

import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView update(@RequestParam("idperson") int idperson) {
        logger.info(idperson);
        ModelAndView mv = new ModelAndView();
        Person person = personService.findPersonByIdperson(idperson);
        mv.addObject("person", person);
        mv.setViewName("jsp/upload/updatePerson");
        return mv;
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    public ModelAndView updatePerson(HttpServletRequest request,
                                     Person person) {
        ModelAndView mv = new ModelAndView();
        //test
        personService.modifyPerson(person);
        mv.addObject("message", "updated user " + person);
        mv.setViewName("/views/success");
        return mv;
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam("idperson") int idperson) {
        ModelAndView mv = new ModelAndView();
        Person person = personService.findPersonByIdperson(idperson);
        //test
        mv.addObject("person", person);
        personService.removeByIdperson(idperson);
        mv.setViewName("views/success");
        return mv;
    }

    @RequestMapping(value = "/{idcenter}/center/list", method = RequestMethod.GET)
    public String listPersonsByCenter(@PathVariable("idcenter") Integer idcenter,
                                      Model model) {
        List<Person> persons = personService.findAllPersonsByCenterId(idcenter);
        persons.forEach(System.out::println);
        model.addAttribute("persons", persons);

        return "jsp/users/personsInCenter";
    }

}
