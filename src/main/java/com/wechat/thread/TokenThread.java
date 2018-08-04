package com.wechat.thread;

import com.wechat.model.AccessToken;
import com.wechat.utils.AccessTokenUtil;
import org.apache.log4j.Logger;

public class TokenThread implements Runnable {
    //微信公众号的凭证和秘钥

    public static final String appID = "wxb92b6517e66c5eda";
    public static final String appSecret = "7953a4803072b35c8e41ed27933f0ecb";
    public static AccessToken access_token=null;
    private static Logger logger = Logger.getLogger(TokenThread.class);

    @Override
    public void run() {
        while(true){
            try {
                //调用工具类获取access_token(每日最多获取100000次，每次获取的有效期为7200秒)
                access_token = AccessTokenUtil.getAccessToken(appID, appSecret);
                if(null != access_token.getToken()){
                    logger.info("accessToken获取成功： " + access_token.getToken());//7000秒之后重新进行获取
                    Thread.sleep((access_token.getExpiresIn()-200)*1000);
                }else{//获取失败时，60秒之后尝试重新获取
                    Thread.sleep(60*1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
