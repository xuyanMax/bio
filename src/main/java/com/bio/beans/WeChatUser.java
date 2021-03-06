package com.bio.beans;

public class WeChatUser {
    // 用户的标识
    private Integer idwechat;

    public Integer getIdwechat() {
        return idwechat;
    }

    public void setIdwechat(int idwechat) {
        this.idwechat = idwechat;
    }

    private String openid;
    // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
    private String subscribe;

    // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    private String subscribe_time;
    // 昵称
    private String nickname;
    // 用户的性别（1是男性，2是女性，0是未知）
    private String sex;
    // 用户所在省份
    private String province;
    // 用户所在城市
    private String city;
    // 用户的语言，简体中文为zh_CN
    private String language;
    // 用户头像
    private String headImgUrl;
    // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String unionid;

    private Integer idperson;

    private String remark;

    private String groupid;

    private String tagid_list;

    private String createtime;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setIdwechat(Integer idwechat) {
        this.idwechat = idwechat;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(String subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Integer getIdperson() {
        return idperson;
    }

    public void setIdperson(Integer idperson) {
        this.idperson = idperson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getTagid_list() {
        return tagid_list;
    }

    public void setTagid_list(String tagid_list) {
        this.tagid_list = tagid_list;
    }

    @Override
    public String toString() {
        return "WeChatUser{" +
                "idwechat=" + idwechat +
                ", openid='" + openid + '\'' +
                ", subscribe='" + subscribe + '\'' +
                ", subscribe_time='" + subscribe_time + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", language='" + language + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", unionid='" + unionid + '\'' +
                ", idperson=" + idperson +
                ", remark='" + remark + '\'' +
                ", groupid='" + groupid + '\'' +
                ", tagid_list='" + tagid_list + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}