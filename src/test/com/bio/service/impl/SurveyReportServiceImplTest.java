package com.bio.service.impl;

import com.bio.beans.SurveyReport;
import com.bio.service.ISurveyReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-05-16 13:27
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class SurveyReportServiceImplTest {

    @Autowired
    private ISurveyReportService surveyReportService;

    @Test
    public void addSurveyReport() {
        SurveyReport report = new SurveyReport();

        report.setFyrs_score("100");
        report.setHigh_risk("101");
        report.setLow_risk("11");
        report.setId(1);
        report.setModelnames("1;2;3;4;5");
        report.setLifetime_score("102");
        report.setIdquestionnaire(1);
        report.setQuestionnaire_score(200);
        surveyReportService.addSurveyReport(report);
    }

    @Test
    public void findSurveyReportByIdquestionnaire() {
        System.out.println(surveyReportService.findSurveyReportByIdquestionnaire(1));
    }
}