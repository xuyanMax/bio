package com.bio.service;

import com.bio.beans.Center;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-21 22:04
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class ICenterServiceTest {

    @Autowired
    private ICenterService centerService;

    @Test
    public void testCenterName() {
        String centerName = "2_上海人类基因组研究中心";
        Center c = centerService.findCenterByCenterName(centerName.substring(centerName.indexOf("_") + 1));
        System.out.println(c);

    }

}