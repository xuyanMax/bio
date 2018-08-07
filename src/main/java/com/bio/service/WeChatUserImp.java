package com.bio.service;

import com.bio.beans.WeChatUser;
import com.bio.dao.IWeChatUserDao;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WeChatUserImp implements IWeChatUserService {
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
    public void modifyWxUser(WeChatUser user) {
        iWeChatUserDao.updateWxUser(user);
    }

    @Override
    public List<WeChatUser> selectAllWxUsers() {
        return iWeChatUserDao.selectAllWxUsers();
    }

    @Override
    public WeChatUser findWxUserByOpenId(String openid) {
        return iWeChatUserDao.selectWxUserByOpenId(openid);
    }

    @Override
    public WeChatUser findWxUserByUnionid(String unionid) {
        return iWeChatUserDao.selectWxUserByUnionid(unionid);
    }

    @Override
    public WeChatUser findWxUserByIdwechat(int idwechat) {
        return iWeChatUserDao.selectWxUserByIdwechat(idwechat);
    }
}
