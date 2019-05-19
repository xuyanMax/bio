package com.bio.dao;

import com.bio.beans.SurveyReport;

/**
 * @Author: xyx
 * @Date: 2019-05-16 13:08
 * @Version 1.0
 */
public interface ISurveyReportDao {

    public SurveyReport selectSurveyByIdquestionnaire(int idquestionnaire);

    public void insertReport(SurveyReport surveyReport);
}
