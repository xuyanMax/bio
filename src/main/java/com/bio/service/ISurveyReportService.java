package com.bio.service;

import com.bio.beans.SurveyReport;

/**
 * @Author: xyx
 * @Date: 2019-05-16 13:25
 * @Version 1.0
 */
public interface ISurveyReportService {

    public void addSurveyReport(SurveyReport report);

    public void addSurveyReport(int idquestionnaire,
                                String low_risk,
                                String high_risk,
                                String fyrs_score,
                                String lifetime_score,
                                int questionnaire_score,
                                String modelnames);

    public SurveyReport findSurveyReportByIdquestionnaire(int idquestionnaire);
}
