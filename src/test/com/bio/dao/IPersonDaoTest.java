package com.bio.dao;

import com.bio.beans.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-01 21:59
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class IPersonDaoTest {

    @Autowired
    private IPersonDao iPersonDao;

    @Test
    public void testInsertPerson() {
        Person p = new Person();
        p.setName("asd");
        p.setSn_in_center("1");
        p.setIdcenter(2);
        p.setGlobal_sn("443005_1_1");

        p.setID_code("12311");
        p.setID_code_cut("11");
        iPersonDao.insertPerson(p);
    }

}