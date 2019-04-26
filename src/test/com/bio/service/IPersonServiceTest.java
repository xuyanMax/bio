package com.bio.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-21 21:47
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class IPersonServiceTest {

    @Autowired
    private IPersonService personService;

    @Test
    public void testCountPersonByCenter(){
        int idcenter = 2;
        int count = personService.countPersonsByIdCenter(idcenter);
        System.out.println(count);
    }

}