package com.bio.controller.admin;

import com.bio.Utils.Utils;
import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ManualInsert {
    @Autowired
    private IPersonService personService;

    @RequestMapping("/manualInsertPage")
    public String goInsertPage(){
        return "jsp/upload/manualInsertion";
    }

    @RequestMapping(value = "/manualInsertion", method = RequestMethod.POST)
    public ModelAndView manualInsertion(HttpServletRequest request,
                                        Person person,
                                        ModelAndView mv){
        if (person == null){
            mv.addObject("message", "输入错误");
            mv.setViewName("jsp/upload/manualInsertion");
        }else {
            //set age
            person.setAge(Utils.getAge(person.getID_code()));
            //set gender
            person.setGender(String.valueOf(Utils.getGender(person.getID_code())));
            //插入数据库...
            insertPerson(person);
            mv.addObject("person", person);
            // test
            System.out.println(person);
            mv.addObject("message", "上传成功，请继续添加");
        }

        return mv;

    }
    // todo: 插入person数据
    public void insertPerson(Person person){
        personService.addPerson(person);
    }


    @RequestMapping(value = "/UploadedPersons", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest httpServletRequest,
                             ModelAndView mv){

        List<Person> persons = personService.findAllPersons();
        // bug??
        mv.addObject("persons", persons);

        mv.setViewName("views/personList");
        return mv;
    }

}
