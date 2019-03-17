package com.bio.service;

import com.bio.beans.Qtnaireversion_riskmodel;

import java.util.List;

/**
 * @Author: xyx
 * @Date: 2019-02-21 00:11
 * @Version 1.0
 */
public interface IQtRiskModelService {

    public List<Qtnaireversion_riskmodel> findRiskModelByVersion(int version);

    public Qtnaireversion_riskmodel findRiskModelByVersionLimitOne(int version);

}
