package com.bio.service;

import com.bio.beans.Answer;
import com.bio.dao.IAnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerImpl implements IAnswerService {
    @Autowired
    private IAnswerDao iAnswerDao;

    @Override
    public void addAnswer(Answer answer) {
        iAnswerDao.insertAnswer(answer);
    }
}
