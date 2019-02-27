package com.bio.dao;

import com.bio.beans.Qtnaireversion_riskmodel;

import java.util.List;

/**
 * @Author: xyx
 * @Date: 2019-02-21 00:02
 * @Version 1.0
 */
public interface IQtRiskModelDao {

    List<Qtnaireversion_riskmodel> selectRiskModelByVersion(int version);

}
