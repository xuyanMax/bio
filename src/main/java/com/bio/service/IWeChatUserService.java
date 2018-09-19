package com.bio.service;

import com.bio.beans.WeChatUser;

import java.util.List;

public interface IWeChatUserService {
    void addWxUser(WeChatUser user);

    void removeWxUserByOpenid(String openid);
    void removeWxUserByIdperson(Integer idperson);

    void modifyWxUserByUnionid(WeChatUser user);

    List<WeChatUser> selectAllWxUsers();

    WeChatUser findWxUserByOpenId(String openid);

    WeChatUser findWxUserByUnionid(String unionid);

    WeChatUser findWxUserByIdwechat(int idwechat);

    WeChatUser findWxUserByIdperson(int idperson);

}
