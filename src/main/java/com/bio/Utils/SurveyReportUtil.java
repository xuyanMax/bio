package com.bio.Utils;

import com.bio.beans.SurveyReport;

/**
 * @Author: xyx
 * @Date: 2019-05-16 20:44
 * @Version 1.0
 */
public class SurveyReportUtil {

    public static SurveyReport createSurveyReport(int idquestionnaire,
                                                  String low_risk,
                                                  String high_risk,
                                                  String fyrs_score,
                                                  String lifetime_score,
                                                  int questionnaire_score,
                                                  String modelnames) {
        SurveyReport surveyReport = new SurveyReport();
        surveyReport.setIdquestionnaire(idquestionnaire);
        surveyReport.setLow_risk(low_risk);
        surveyReport.setHigh_risk(high_risk);
        surveyReport.setFyrs_score(fyrs_score);
        surveyReport.setLifetime_score(lifetime_score);
        surveyReport.setQuestionnaire_score(questionnaire_score);
        surveyReport.setModelnames(modelnames);
        return surveyReport;
    }
}
