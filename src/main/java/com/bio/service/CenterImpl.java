package com.bio.service;

import com.bio.beans.Center;
import com.bio.dao.ICenterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CenterImpl implements ICenterService {
    @Autowired
    private ICenterDao centerDao;

    @Override
    public int findPersonInCentersByCenterid(int idcenter) {
        return centerDao.selectCenterIdById(idcenter);
    }

    @Override
    public Center findPersonInCentersByIdperson(int idperson) {
        return centerDao.selectCenterByIdperson(idperson);
    }
}
