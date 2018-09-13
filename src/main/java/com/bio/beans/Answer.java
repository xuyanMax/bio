package com.bio.beans;

public class Answer {
    Integer idanswers;
    Integer idperson;
    Integer idquestionnaire;
    Integer idquestion;
    String answers;

    public Integer getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(Integer idquestion) {
        this.idquestion = idquestion;
    }

    public Integer getIdanswers() {
        return idanswers;
    }

    public void setIdanswers(Integer idanswers) {
        this.idanswers = idanswers;
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

    @Override
    public String toString() {
        return "Answer{" +
                "idanswers=" + idanswers +
                ", idperson=" + idperson +
                ", idquestionnaire=" + idquestionnaire +
                ", idquestion=" + idquestion +
                ", answers='" + answers + '\'' +
                '}';
    }
}
