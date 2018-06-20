package com.wechat.model.message.request;

public class ImageMessage extends BaseMessage{
    //image url
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
