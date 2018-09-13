package com.bio.beans;

public class Questionnaire {
    Integer idperson;
    Integer idquestionnaire;
    String questionnaire_num;
    String filling_time;
    String qtnaire_version;
    Integer score;

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

    public String getQtnaire_version() {
        return qtnaire_version;
    }

    public void setQtnaire_version(String qtnaire_version) {
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
                ", qtnaire_version='" + qtnaire_version + '\'' +
                ", score=" + score +
                '}';
    }
}
