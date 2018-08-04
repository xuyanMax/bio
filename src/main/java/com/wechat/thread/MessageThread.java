package com.wechat.thread;

import com.wechat.model.AccessToken;
import com.wechat.utils.AccessTokenUtil;
import com.wechat.model.WeChatUser;
import com.wechat.utils.CoreService;
import com.wechat.utils.WeChatUtils;

public class MessageThread implements Runnable {
    private static final String appID = "wxb92b6517e66c5eda";
    private static final String appSecret = "7953a4803072b35c8e41ed27933f0ecb";
    private static AccessToken access_token=null;
    @Override
    public void run() {
        access_token = AccessTokenUtil.getAccessToken(appID, appSecret);
        if (access_token != null){

        }

    }
}
