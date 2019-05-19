package com.bio.service.impl;

import com.bio.Utils.SurveyReportUtil;
import com.bio.beans.SurveyReport;
import com.bio.dao.ISurveyReportDao;
import com.bio.service.ISurveyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xyx
 * @Date: 2019-05-16 13:26
 * @Version 1.0
 */
@Service
public class SurveyReportServiceImpl implements ISurveyReportService {

    @Autowired
    private ISurveyReportDao surveyReportDao;

    @Override
    public void addSurveyReport(int idquestionnaire,
                                String low_risk,
                                String high_risk,
                                String fyrs_score,
                                String lifetime_score,
                                int questionnaire_score,
                                String modelnames) {
        SurveyReport report = SurveyReportUtil.createSurveyReport(
                idquestionnaire, low_risk,
                high_risk,
                fyrs_score,
                lifetime_score,
                questionnaire_score,
                modelnames);
        addSurveyReport(report);
    }

    @Override
    public void addSurveyReport(SurveyReport report) {
        surveyReportDao.insertReport(report);
    }

    @Override
    public SurveyReport findSurveyReportByIdquestionnaire(int idquestionnaire) {
        return surveyReportDao.selectSurveyByIdquestionnaire(idquestionnaire);
    }
}
