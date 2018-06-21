package com.wechat.thread;

import com.wechat.model.AccessToken;
import com.wechat.utils.AccessTokenUtil;
import com.wechat.model.WeChatUser;
import com.wechat.utils.CoreService;
import com.wechat.utils.WeChatUtils;

public class MessageThread implements Runnable {
    private static final String appID = "wx0f81f68f813bd68d";
    private static final String appSecret = "c9d7f54ec1d0642d187141636ba69af2";
    private static AccessToken access_token=null;
    @Override
    public void run() {
        access_token = AccessTokenUtil.getAccessToken(appID, appSecret);
        if (access_token != null){

        }

    }
}
