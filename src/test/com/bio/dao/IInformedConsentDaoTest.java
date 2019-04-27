package com.bio.dao;

import com.bio.beans.InformedConsent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: xyx
 * @Date: 2019-04-27 22:31
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class IInformedConsentDaoTest {

    @Autowired
    private IInformedConsentDao informedConsentDao;

    @Test
    public void testSelectInformedConsent1() {

        InformedConsent res = informedConsentDao.selectInformedConsent();

        System.out.println(res);

    }


}