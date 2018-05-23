package com.bio.controller.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManualInsertData {

    @RequestMapping("/manualInsertPage")
    public String goInsertPage(){
        return "js/upload/manualInsertion";
    }

    @RequestMapping("/manualInsertion")
    public String manualInsertion(){
        return "";
    }
}
