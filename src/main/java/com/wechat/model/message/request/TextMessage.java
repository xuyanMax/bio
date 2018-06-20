package com.wechat.model.message.request;

//https://blog.csdn.net/qq_25646191/article/details/78856639
public class TextMessage extends BaseMessage {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
