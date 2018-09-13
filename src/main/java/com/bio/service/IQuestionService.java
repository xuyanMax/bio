package com.bio.service;

import com.bio.beans.Questionnaire;

public interface IQuestionService {
    void addQuestionAnswer(Questionnaire questionnaire);
    Questionnaire findQuestionByFillingTime(String filling_time);
}
