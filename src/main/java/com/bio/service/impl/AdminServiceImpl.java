package com.bio.service.impl;

import com.bio.beans.Admin;
import com.bio.dao.IAdminDao;
import com.bio.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    IAdminDao adminDao;

    @Override
    public Admin findAdminUser(int idperson) {
        return adminDao.selectAdminUser(idperson);
    }
}
