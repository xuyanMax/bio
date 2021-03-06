package com.bio.service.impl;

import com.bio.beans.Qtnaireversion_riskmodel;
import com.bio.dao.IQtRiskModelDao;
import com.bio.service.IQtRiskModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xyx
 * @Date: 2019-02-21 00:12
 * @Version 1.0
 */
@Service
public class QtRiskModelServiceImpl implements IQtRiskModelService {

    @Autowired
    private IQtRiskModelDao iQtRiskModelDao;

    @Override
    public List<Qtnaireversion_riskmodel> findRiskModelByVersion(int version) {
        return iQtRiskModelDao.selectRiskModelByVersion(version);
    }

    @Override
    public Qtnaireversion_riskmodel findRiskModelByVersionLimitOne(int version) {
        return iQtRiskModelDao.selectRiskModelByVersionLimitOne(version);
    }
}
