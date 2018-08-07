package com.bio.dao;

import com.bio.beans.WeChatUser;

import java.util.List;

public interface IWeChatUserDao {
    void insertWxUser(WeChatUser user);

    //删除指定IdPerson
    void deleteWxUserByOpenid(String openid);
    //更新某Person
    void updateWxUser(WeChatUser user);

    List<WeChatUser> selectAllWxUsers();

    WeChatUser selectWxUserByOpenId(String openid);

    WeChatUser selectWxUserByUnionid(String unionid);
    WeChatUser selectWxUserByIdwechat(int idwechat);

}
