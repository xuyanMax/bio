package com.bio.beans;

/**
 * @Author: xyx
 * @Date: 2019-05-16 12:36
 * @Version 1.0
 */
public class SurveyReport {

    private int id;

    private int idquestionnaire;

    private int questionnaire_score;

    private String low_risk;

    private String high_risk;

    private String fyrs_score;

    private String lifetime_score;

    private String modelnames;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdquestionnaire() {
        return idquestionnaire;
    }

    public void setIdquestionnaire(int idquestionnaire) {
        this.idquestionnaire = idquestionnaire;
    }

    public int getQuestionnaire_score() {
        return questionnaire_score;
    }

    public void setQuestionnaire_score(int questionnaire_score) {
        this.questionnaire_score = questionnaire_score;
    }

    public String getLow_risk() {
        return low_risk;
    }

    public void setLow_risk(String low_risk) {
        this.low_risk = low_risk;
    }

    public String getHigh_risk() {
        return high_risk;
    }

    public void setHigh_risk(String high_risk) {
        this.high_risk = high_risk;
    }

    public String getFyrs_score() {
        return fyrs_score;
    }

    public void setFyrs_score(String fyrs_score) {
        this.fyrs_score = fyrs_score;
    }

    public String getLifetime_score() {
        return lifetime_score;
    }

    public void setLifetime_score(String lifetime_score) {
        this.lifetime_score = lifetime_score;
    }

    public String getModelnames() {
        return modelnames;
    }

    public void setModelnames(String modelnames) {
        this.modelnames = modelnames;
    }

    @Override
    public String toString() {
        return "SurveyReport{" +
                "id=" + id +
                ", idquestionnaire=" + idquestionnaire +
                ", questionnaire_score=" + questionnaire_score +
                ", low_risk='" + low_risk + '\'' +
                ", high_risk='" + high_risk + '\'' +
                ", fyrs_score='" + fyrs_score + '\'' +
                ", lifetime_score='" + lifetime_score + '\'' +
                ", modelnames='" + modelnames + '\'' +
                '}';
    }
}
