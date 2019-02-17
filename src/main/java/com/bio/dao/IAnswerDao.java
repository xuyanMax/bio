package com.bio.dao;

import com.bio.beans.Answer;

import java.util.List;

public interface IAnswerDao {
    void insertAnswer(Answer answer);
    List<Answer> selectByIdquestionIdquestionnaire(int idquestion, int idquestionnaire);
}
