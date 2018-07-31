package com.sms;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.DBUtils;
import com.bio.Utils.PersonInfoUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class SmsBase {
    public static String USERNAME="15151528348"; //注册时用户名（最好用英文，中文名字没测试。）
    public static String PSSD="123qweasd";	      //注册时密码
    //{123qweasd:57BA172A6BE125CCA2F449826F9980CA}
    public static String URL_SMS = "http://www.lx598.com/sdk/send?accName=ACCNAME&accPwd=ACCPWD&aimcodes=AIMCODES&content=CONTENT【签名】&dataType=DATATYPE";

    public static String content = "您好，短信验证码是${6},${2}分钟内有效，请勿告知他人，联系电话${11}";
    public static String SIGNATURE = "人类基因组南方中心";
    public static String Json = "json";
    public static String STRING = "string";

    /**
     * 发送短信
     * accName 用户名
     * accPwd 密码
     * aimcodes 手机号多个手机号之间英文半角逗号隔开
     * content 内容后加签名
     * schTime 定时时间格式如：2010-01-01 08:00:00
     * @return 服务端返回的结果 ok:业务id 或者 错误代码
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr){
        URL url = null;
        HttpsURLConnection httpUrlConn = null;
        StringBuffer buffer = new StringBuffer();
        try {
            url = new URL(requestUrl);
            httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 从输入流读取返回内容
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private static String URL_CHECK = "http://sdk.lx198.com/sdk/qryReport?accNAME=ACCNAME&accPwd=ACCPWD";
    public static String sendMsgCheck(){
        String url_check = URL_CHECK
                .replace("ACCNAME", USERNAME)
                .replace("ACCPWD", PersonInfoUtils.md5(PSSD))
                .replace("CONTENT", content)
                .replace("DATATYPE", Json);
        return httpRequest(url_check, "POST", null);
    }


}
