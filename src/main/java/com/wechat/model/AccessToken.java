package com.wechat.model;

public class AccessToken {
    private String token;// 获取到的凭证

    private int expiresIn;//凭证有效期,秒

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
