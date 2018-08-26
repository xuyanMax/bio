package com.bio.dao;

import com.bio.beans.WeChatUser;
import com.wechat.utils.WeChatUtils;

import java.util.List;

public interface IWeChatUserDao {
    void insertWxUser(WeChatUser user);

    //删除指定IdPerson
    void deleteWxUserByOpenid(String openid);
    //更新某Person
    void updateWxUserByUnionid(WeChatUser user);

    List<WeChatUser> selectAllWxUsers();

    WeChatUser selectWxUserByOpenid(String openid);

    WeChatUser selectWxUserByUnionid(String unionid);

    WeChatUser selectWxUserByIdwechat(int idwechat);

    WeChatUser selectWxUserByIdperson(int idperson);

}
