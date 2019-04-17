package com.bio.service.impl;

import com.bio.beans.Center;
import com.bio.dao.ICenterDao;
import com.bio.service.ICenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterImpl implements ICenterService {

    @Autowired
    private ICenterDao centerDao;

    @Override
    public Center findPersonInCentersByCenterid(int idcenter) {
        return centerDao.selectCenterIdById(idcenter);
    }

    @Override
    public Center findPersonInCentersByIdperson(int idperson) {
        return centerDao.selectCenterByIdperson(idperson);
    }

    @Override
    public List<Center> findNoCenters() {
        return centerDao.selectCentersByNoCenters();
    }

    @Override
    public Center findCenterByCenterName(String center) {
        return centerDao.selectCenterByCenterName(center);
    }

    @Override
    public List<Center> findCentersBy1And2() {
        return centerDao.selectCentersBy1And2();
    }
}
