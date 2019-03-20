package com.bio.beans;

public class Questionnaire {

    Integer idperson;

    Integer idquestionnaire;

    String questionnaire_num;

    String filling_time;

    Integer qtnaire_version;

    Integer score;

    String lifetime_score;

    String fyrs_score;

    String fyrs_risk;

    String lifetime_risk;

    String risk_crcmale;

    String risk_crcfemale;

    String risk_bra;

    public String getLifetime_score() {
        return lifetime_score;
    }

    public void setLifetime_score(String lifetime_score) {
        this.lifetime_score = lifetime_score;
    }

    public String getFyrs_score() {
        return fyrs_score;
    }

    public void setFyrs_score(String fyrs_score) {
        this.fyrs_score = fyrs_score;
    }

    public String getFyrs_risk() {
        return fyrs_risk;
    }

    public void setFyrs_risk(String fyrs_risk) {
        this.fyrs_risk = fyrs_risk;
    }

    public String getLifetime_risk() {
        return lifetime_risk;
    }

    public void setLifetime_risk(String lifetime_risk) {
        this.lifetime_risk = lifetime_risk;
    }

    public String getRisk_crcmale() {
        return risk_crcmale;
    }

    public void setRisk_crcmale(String risk_crcmale) {
        this.risk_crcmale = risk_crcmale;
    }

    public String getRisk_crcfemale() {
        return risk_crcfemale;
    }

    public void setRisk_crcfemale(String risk_crcfemale) {
        this.risk_crcfemale = risk_crcfemale;
    }

    public String getRisk_bra() {
        return risk_bra;
    }

    public void setRisk_bra(String risk_bra) {
        this.risk_bra = risk_bra;
    }

    public Integer getIdperson() {
        return idperson;
    }

    public void setIdperson(Integer idperson) {
        this.idperson = idperson;
    }

    public Integer getIdquestionnaire() {
        return idquestionnaire;
    }

    public void setIdquestionnaire(Integer idquestionnaire) {
        this.idquestionnaire = idquestionnaire;
    }

    public String getQuestionnaire_num() {
        return questionnaire_num;
    }

    public void setQuestionnaire_num(String questionnaire_num) {
        this.questionnaire_num = questionnaire_num;
    }

    public String getFilling_time() {
        return filling_time;
    }

    public void setFilling_time(String filling_time) {
        this.filling_time = filling_time;
    }

    public Integer getQtnaire_version() {
        return qtnaire_version;
    }

    public void setQtnaire_version(Integer qtnaire_version) {
        this.qtnaire_version = qtnaire_version;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "idperson=" + idperson +
                ", idquestionnaire=" + idquestionnaire +
                ", questionnaire_num='" + questionnaire_num + '\'' +
                ", filling_time='" + filling_time + '\'' +
                ", qtnaire_version=" + qtnaire_version +
                ", score=" + score +
                ", lifetime_score='" + lifetime_score + '\'' +
                ", fyrs_score='" + fyrs_score + '\'' +
                ", fyrs_risk='" + fyrs_risk + '\'' +
                ", lifetime_risk='" + lifetime_risk + '\'' +
                ", risk_crcmale='" + risk_crcmale + '\'' +
                ", risk_crcfemale='" + risk_crcfemale + '\'' +
                ", risk_bra='" + risk_bra + '\'' +
                '}';
    }
}
