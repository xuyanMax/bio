package com.bio.service.impl;

import com.bio.beans.RiskModel;
import com.bio.dao.IRiskModelDao;
import com.bio.service.IRiskModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xyx
 * @Date: 2019-02-21 01:04
 * @Version 1.0
 */
@Service
public class RiskModelServiceImpl implements IRiskModelService {

    @Autowired
    private IRiskModelDao iRiskModelDao;

    @Override
    public RiskModel findRiskModelByIdriskmodel(int idriskmodel) {
        return iRiskModelDao.selectByIdriskmodel(idriskmodel);
    }
}
