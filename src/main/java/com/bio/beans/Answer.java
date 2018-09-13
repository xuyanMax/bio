package com.bio.beans;

public class Answer {
    Integer idanswer;
    Integer idperson;
    Integer idquestionnaire;
    String answers;

    public Integer getIdanswer() {
        return idanswer;
    }

    public void setIdanswer(Integer idanswer) {
        this.idanswer = idanswer;
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

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
