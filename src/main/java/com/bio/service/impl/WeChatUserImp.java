package com.bio.service.impl;

import com.bio.beans.WeChatUser;
import com.bio.dao.IWeChatUserDao;
import com.bio.service.IWeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeChatUserImp implements IWeChatUserService {
    @Autowired
    private IWeChatUserDao iWeChatUserDao;

    @Override
    public void addWxUser(WeChatUser user) {
        iWeChatUserDao.insertWxUser(user);
    }

    @Override
    public void removeWxUserByOpenid(String openid) {
        iWeChatUserDao.deleteWxUserByOpenid(openid);
    }

    @Override
    public void removeWxUserByIdperson(Integer idperson) {
        iWeChatUserDao.deleteWxUserByIdperson(idperson);
    }

    @Override
    public void modifyWxUserByUnionid(WeChatUser user) {
        iWeChatUserDao.updateWxUserByUnionid(user);
    }

    @Override
    public List<WeChatUser> selectAllWxUsers() {
        return iWeChatUserDao.selectAllWxUsers();
    }

    @Override
    public WeChatUser findWxUserByOpenId(String openid) {
        return iWeChatUserDao.selectWxUserByOpenid(openid);
    }

    @Override
    public WeChatUser findWxUserByUnionid(String unionid) {
        return iWeChatUserDao.selectWxUserByUnionid(unionid);
    }

    @Override
    public WeChatUser findWxUserByIdperson(int idperson) {
        return iWeChatUserDao.selectWxUserByIdperson(idperson);
    }

    @Override
    public WeChatUser findWxUserByIdwechat(int idwechat) {
        return iWeChatUserDao.selectWxUserByIdwechat(idwechat);
    }
}
