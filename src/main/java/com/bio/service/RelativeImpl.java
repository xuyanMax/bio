package com.bio.service;

import com.bio.beans.Relative;
import com.bio.dao.IRelativeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelativeImpl implements IRelativeService {
    @Autowired
    private IRelativeDao iRelativeDao;

    @Override
    public void addRelative(Relative relative) {
        iRelativeDao.insertRelative(relative);
    }

    @Override
    public void removeRelativeByIdperson1AndIdperson2(Integer idperson1, Integer idperson2) {
        iRelativeDao.deleteRelativeByIdperson1(idperson1, idperson2);
    }

    @Override
    public List<Relative> findRelativesByIdperson1(Integer idperson1) {
        return iRelativeDao.selectRelativesByIdperson1(idperson1);
    }

    @Override
    public Relative findRelativesByIdperson2(Integer idperson2) {
        return iRelativeDao.selectRelativesByIdperson2(idperson2);
    }
}
