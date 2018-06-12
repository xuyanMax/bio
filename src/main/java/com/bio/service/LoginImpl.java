package com.bio.service;

import com.bio.beans.LoginItem;
import com.bio.dao.ILoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginImpl implements ILoginService {
    @Autowired
    private ILoginDao loginDao;
    @Override
    public void addLoginItem(LoginItem loginItem) {
        loginDao.insertLoginItem(loginItem);
    }
}
