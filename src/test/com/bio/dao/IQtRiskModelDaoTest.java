package com.bio.dao;

import com.bio.beans.Qtnaireversion_riskmodel;
import com.bio.service.IQtRiskModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-22 23:48
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class IQtRiskModelDaoTest {

    @Autowired
    private IQtRiskModelService qtRiskModelService;

    @Test
    public void testSelectByVersion(){

        Qtnaireversion_riskmodel riskmodel = qtRiskModelService.findRiskModelByVersionLimitOne(5);
        System.out.println(riskmodel);
    }

}