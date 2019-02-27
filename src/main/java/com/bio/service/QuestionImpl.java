package com.bio.service;

import com.bio.beans.Questionnaire;
import com.bio.dao.IQDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionImpl implements IQuestionService {

    @Autowired
    IQDao iqDao;

    @Override
    public void addQuestionAnswer(Questionnaire questionnaire) {
        iqDao.insertQuestion(questionnaire);
    }

    @Override
    public Questionnaire findQuestionByFillingTime(String filling_time) {
        return iqDao.selectQuestion(filling_time);
    }

    @Override
    public void modifyQuestionnaire(Questionnaire questionnaire) {
        iqDao.updateQuestion(questionnaire);
    }
}
