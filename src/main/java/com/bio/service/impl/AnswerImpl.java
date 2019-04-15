package com.bio.service.impl;

import com.bio.beans.Answer;
import com.bio.dao.IAnswerDao;
import com.bio.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerImpl implements IAnswerService {
    @Autowired
    private IAnswerDao iAnswerDao;

    @Override
    public void addAnswer(Answer answer) {
        iAnswerDao.insertAnswer(answer);
    }

    @Override
    public List<Answer> findByIdquestionIdQuestionnaire(int idquestion, int idquestionnaire) {
        return iAnswerDao.selectByIdquestionIdquestionnaire(idquestion, idquestionnaire);
    }
}
