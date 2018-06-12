package com.bio.service;

import com.bio.beans.Admin;
import com.bio.dao.IAdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {
    @Autowired
    IAdminDao adminDao;
    @Override
    public Admin selectAdminUser(int idperson) {
        return adminDao.findAdminUser(idperson);
    }
}
