package com.bio.controller.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.ClientInfoUtils;
import com.bio.beans.*;
import com.bio.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: xyx
 * @Date: 2019-03-13 20:58
 * @Version 1.0
 */
@Controller
public class HomeTest {

    private static Logger logger = Logger.getLogger(HomeTest.class.getName());
    @Autowired
    ICenterService centerService;
    @Autowired
    IQuestionService questionService;
    @Autowired
    IAnswerService answerService;
    @Autowired
    IRelativeService relativeService;
    @Autowired
    IQtRiskModelService qtRiskModelService;
    @Autowired
    IRiskModelService riskModelService;

    @RequestMapping("/testInsertAnswer")
    public String testInsertAnswer() {

        JSONObject surveyJSON = JSON.parseObject("{\"1\":\"asdasd\",\"3\":\"1\",\"4\":\"190\",\"5\":\"100\",\"35\":\"3\",\"67\":[\"2\",\"4\"],\"89\":[{\"关系\":\"同父母的兄弟姐妹\"}],\"91\":\"0\"}");
        Person user = new Person();
        user.setIdperson(2);
        Questionnaire questionnaire = new Questionnaire();

        questionnaire.setFilling_time(ClientInfoUtils.getCurrDatetime());
        questionnaire.setIdperson(user != null ? user.getIdperson() : 1);
        Center c = centerService.findPersonInCentersByIdperson(user.getIdperson());
        questionnaire.setQtnaire_version(c.getIdcenter());
        questionnaire.setScore(0);

        logger.info(questionnaire);

        String filling_time = questionnaire.getFilling_time();
        questionService.addQuestionAnswer(questionnaire);

        questionnaire = questionService.findQuestionByFillingTime(filling_time);

        logger.info(questionnaire);

        for (Map.Entry<String, Object> item : surveyJSON.entrySet()) {
            logger.info(item.getKey());
            logger.info(item.getValue());

            if (questionnaire != null) {
                Answer answer = new Answer();
                answer.setIdquestion(Integer.valueOf(item.getKey()));
                answer.setAnswers(item.getValue().toString());
                answer.setIdperson(questionnaire.getIdperson());
                answer.setIdquestionnaire(questionnaire.getIdquestionnaire() != null ? questionnaire.getIdquestionnaire() : 1);
                logger.info(answer);
                answerService.addAnswer(answer);
            }
        }
        return "../index";
    }

    @RequestMapping("/testQtnaireversion_riskmodel")
    public String testQtnaireversion_riskmodel(HttpServletRequest request) {

        List<Qtnaireversion_riskmodel> riskModelList = qtRiskModelService.findRiskModelByVersion(3);
        for (Qtnaireversion_riskmodel qtnaireversionRiskmodel : riskModelList) {
            logger.info(qtnaireversionRiskmodel);
            RiskModel rm = riskModelService.findRiskModelByIdriskmodel(qtnaireversionRiskmodel.getIdriskmodel());
            logger.info(rm);
            String sql = rm.getSqlselect_factor();
            logger.info("【sql】=" + sql);
        }

        return "../index";

    }

    @RequestMapping("/testUpdateQustionnaireById")
    public String testUpdateQustionnaireById(HttpServletRequest request) {

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setScore(50);
        questionnaire.setIdquestionnaire(49);
        questionnaire.setFilling_time("2018-10-25 11:39:99");
        questionnaire.setIdperson(999);
        questionnaire.setQtnaire_version(2);
        questionService.modifyQuestionnaire(questionnaire);
        logger.info(questionnaire);

        return "../index";

    }
}
