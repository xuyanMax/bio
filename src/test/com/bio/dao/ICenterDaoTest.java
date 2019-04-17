package com.bio.dao;

import com.bio.beans.Center;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-18 02:26
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class ICenterDaoTest {

    @Autowired
    private ICenterDao iCenterDao;

    @Test
    public void testQuery() {
        String name = "北京市";
        Center center = iCenterDao.selectCenterByCenterName(name);
        System.out.println(center);
    }

    @Test
    public void testSelectCentersBy1And2() {
        List<Center> centerList = iCenterDao.selectCentersBy1And2();
        for (Center c : centerList)
            System.out.println(c);
    }

}