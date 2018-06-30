package com.wechat.utils;

import com.alibaba.fastjson.JSONObject;
import com.wechat.model.AccessToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.InputStream;
public class AccessTokenUtil {

    /**
     * 获取access_token: 公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
     * @param appID 微信公众号凭证
     * @param appSecret 微信公众号凭证秘钥
     * @return
     */

    public static AccessToken getAccessToken(String appID, String appSecret) {
        AccessToken token = new AccessToken();
        // 访问微信服务器
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + appID
                + "&secret="
                + appSecret;

        try {
            URL getUrl=new URL(url);
            HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);

            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);

            String message = new String(b, "UTF-8");
            JSONObject json = JSONObject.parseObject(message);
            token.setToken(json.getString("access_token"));
            token.setExpiresIn(new Integer(json.getString("expires_in")));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
