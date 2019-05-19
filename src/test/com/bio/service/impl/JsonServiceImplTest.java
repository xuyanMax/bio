package com.bio.service.impl;

import com.bio.service.IJsonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-05-16 10:19
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class JsonServiceImplTest {

    @Autowired
    private IJsonService jsonService;

    @Test
    public void findJsonByVersion() {
        System.out.println(jsonService.findJsonByVersion(1));
    }
}