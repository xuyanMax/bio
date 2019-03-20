package com.bio.dao;

import com.bio.beans.Questionnaire;

public interface IQDao {

    void insertQuestion(Questionnaire questionnaire);

    Questionnaire selectQuestion(String filling_time);

    void updateQuestion(Questionnaire questionnaire);

}
