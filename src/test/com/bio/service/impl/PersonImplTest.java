package com.bio.service.impl;

import com.bio.service.IPersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-19 00:34
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class PersonImplTest {

    @Autowired
    private IPersonService personService;

    @Test
    public void findCenterAdminInfo() {
        int idperson = 3;
        System.out.println(personService.findCenterAdminInfo(idperson));
    }
}