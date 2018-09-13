package com.bio.controller.admin;

import com.bio.Utils.ClientInfoUtils;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Exam;
import com.bio.beans.Person;
import com.bio.service.IExamService;
import com.bio.service.IPersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/admin")
@SessionAttributes({"user", "username"})
public class ManualInsert {
    Logger logger = Logger.getLogger(ManualInsert.class);
    @Autowired
    private IPersonService personService;
    @Autowired
    private IExamService examService;

    @RequestMapping("/manualInsertPage")
    public ModelAndView goInsertPage(@ModelAttribute("username") String username){
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", username);
        mv.setViewName("jsp/upload/manualInsertion");
        return mv;
    }

    @RequestMapping(value = "/manualInsertion")
    public ModelAndView manualInsertion(HttpServletRequest request,
                                        Person person,
                                        @ModelAttribute("username") String username){

        ModelAndView mv = new ModelAndView();
        mv.addObject("username", username);
        if (person == null){
            //暂时前端无用message
            mv.addObject("message", "输入错误");
        }else {
            String ID_code = person.getID_code();

            person.setID_code(PersonInfoUtils.md5(ID_code));
            person.setGender(PersonInfoUtils.getGender(ID_code));
            person.setID_code_cut(ID_code.substring(15));

            personService.addPerson(person);

            mv.addObject("message", "上传成功，请继续添加");

            Person p = personService.findPersonByID_code(person.getID_code());

            Exam exam = new Exam();
            exam.setBarcode(person.getBarcode());
            exam.setIdperson(p.getIdperson());
            exam.setInput_date(ClientInfoUtils.getCurrDatetime());
            logger.info(exam);
            examService.addExam(exam);

        }
        mv.setViewName("/jsp/upload/manualInsertion");
        return mv;
    }
}
