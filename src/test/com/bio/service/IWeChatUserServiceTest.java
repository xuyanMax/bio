package com.bio.service;

import com.bio.beans.WeChatUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-21 23:04
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class IWeChatUserServiceTest {

    @Autowired
    IWeChatUserService weChatUserService;

    @Test
    public void testInsertWechatUser() {
        WeChatUser wxuser = new WeChatUser();
        wxuser.setUnionid("xxxx");
        wxuser.setOpenid("abx");
        wxuser.setSubscribe("1100420");
        wxuser.setSubscribe_time("123123123123)))");
        wxuser.setLanguage("zn");
        wxuser.setHeadImgUrl("http://***.com");
        wxuser.setNickname("xyx");
        wxuser.setIdperson(3);
        wxuser.setSex("男");
        wxuser.setCity("石家庄");
        wxuser.setProvince("河北省");

        weChatUserService.addWxUser(wxuser);

    }

}