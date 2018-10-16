package com.bio.service;

import com.bio.beans.Exam;
import com.bio.dao.IExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements IExamService {
    @Autowired
    IExamDao examDao;

    @Override
    public void addExam(Exam exam) {
        examDao.insertExam(exam);
    }
}
