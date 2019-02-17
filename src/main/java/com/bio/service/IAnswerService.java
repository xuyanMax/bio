package com.bio.service;

import com.bio.beans.Answer;

import java.util.List;

public interface IAnswerService {

    void addAnswer(Answer answer);

    List<Answer> findByIdquestionIdQuestionnaire(int idquestion, int idquestionnaire);
}
