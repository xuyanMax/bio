package com.sms;
import com.bio.Utils.PersonInfoUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SmsBase {
    private static String USERNAME="15151528348"; //注册时用户名（最好用英文，中文名字没测试。）
    private static String PSSD="123qweasd";	      //注册时密码//{123qweasd:57BA172A6BE125CCA2F449826F9980CA}
    private static String SIGNATURE = "人类基因组南方中心";
    public static String URL_SMS = "http://www.lx598.com/sdk/send?accName="
            + USERNAME
            +"&accPwd="+PersonInfoUtils.md5(PSSD)
            +"&aimcodes=AIMCODES&content=CONTENT【"
            +SIGNATURE
            +"】&dataType=DATATYPE";
    public static String content = "您好，短信验证码是${6}, 2分钟内有效，请勿告知他人。";
    public static String Json = "json";
    public static String STRING = "string";
    public static Logger logger = Logger.getLogger(SmsBase.class);

    /**
     * 发送短信
     * accName 用户名
     * accPwd 密码
     * aimcodes 手机号多个手机号之间英文半角逗号隔开
     * content 内容后加签名
     * schTime 定时时间格式如：2010-01-01 08:00:00
     * @return 服务端返回的结果 ok:业务id 或者 错误代码
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr, String vcode){
        String content_vcode = content.replace("${6}", vcode);

        String reqUrl = requestUrl
                .replace("CONTENT", content_vcode)
                .replace("签名", SIGNATURE)
                .replace("DATATYPE", STRING)
                .replace(" ","");//solved Server returned HTTP response code: 400 for URL

        logger.info("SMS requests " + reqUrl + ", to send vcode");
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

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
        logger.info(buffer.toString());
        return buffer.toString();
    }

    private static String URL_CHECK = "http://sdk.lx198.com/sdk/qryReport?accNAME=ACCNAME&accPwd=ACCPWD";
    public static String sendMsgCheck(){
        return httpRequest(URL_CHECK, "POST", null, null);
    }


}
