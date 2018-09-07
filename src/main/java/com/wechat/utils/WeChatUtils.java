package com.wechat.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wechat.model.AccessToken;
import com.wechat.model.OAuthInfo;
import com.bio.beans.WeChatUser;
import com.wechat.model.button.Menu;
import org.apache.log4j.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * 公众平台通用接口工具类
 * 参考: https://blog.csdn.net/lyq8479/article/details/9841371
 */
public class WeChatUtils {
    private static final Logger logger = Logger.getLogger(WeChatUtils.class.getName());
    private static String APPID_URL = "wx73e0725a818a8ccb";
    private static String SECRET_URL = "570d28bcda358b8c8d7021e8ee18f184";

    //微信token的获取
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //当我们点击，每个人都有的按钮, 通过url后面的域名redirect_uri=http://population.chgc.sh.cn/user/info
    //进入我们的 task/technician/check  这个方法传一个code值过去
    public static String url_snsapi_userinfo = "https://open.weixin.qq.com/connect/oauth2/authorize?"+ "appid=APPID&redirect_uri="
            + "REDIRECT_URL&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";

    public static String REDIRECT_URL = "http://population.chgc.sh.cn/user/inf";

    // 通过扫描微信二维码登陆
    public static String scan_auth_url = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";

    /*getAccessToken + httpRequest == AccessTokenUtil*/
    // 获取access_token的接口地址（GET） 限200（次/天）
    public static AccessToken getAccessToken(String appId, String appSecret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}"+ jsonObject.getIntValue("errcode")+ jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {

        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
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
            jsonObject = JSONObject.parseObject(buffer.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    /**
     * 创建菜单
     *
     * @param menu 菜单实例，可以包含post/get到微信的查询接口按钮
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.toJSONString(menu);
        logger.info("jsonMenu: " + jsonMenu);
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            logger.info("MenuJSONObject=" + jsonObject.toJSONString());
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                logger.error("创建菜单失败 errcode:{} errmsg:{}");
            }
        }

        return result;
    }

    private static String get_openId_url_msg = "https://api.weixin.qq.com/cgi-bin/user/info?" +
            "access_token=ACCESS_TOKEN&" +
            "openid=OPENID&lang=zh_CN";
    /*
     * 关注公众号的用户发送消息，获取用户部分信息
     * */
    public static WeChatUser getWeChatUser(String openId, String accessToken){
        //1.
        String url = get_openId_url_msg.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);

        JSONObject jsonObject = httpRequest(url, "GET", null);

        if (jsonObject != null) {
            logger.info("WeChatUser="+jsonObject.toJSONString());
            WeChatUser user = composeWeChatUser(jsonObject);
            logger.info(user);

            return user;
        }
        else{
            logger.warn("返回JSONObject=Null");
            return null;
        }
    }
    //todo:
    //在创建自定义菜单时指定URL为网页授权接口：
    public static final String url_snsapi_base = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=APPID&" +
            "redirect_uri=REDIRECT_URI&" +
            "response_type=code&" +
            "scope=snsapi_base&" +
            "state=s";

    /**
     * 通过code获取Access_Token
     * @param code
     * @return  OAuthInfo
     * */
    public static OAuthInfo getOAuthInfoByCode(String code){

        String url = WeChatConstants.GET_WEBAUTH_URL
                                    .replace("CODE",code)
                                    .replace("APPID", APPID_URL)
                                    .replace("APP_SECRET", SECRET_URL);

        JSONObject JSONOAuth = httpRequest(url, "GET", null);
        logger.info(JSONOAuth);
        OAuthInfo authInfo = composeAuthInfo(JSONOAuth);
        logger.info(authInfo);
        return authInfo;

    }

    // OAuth2 授权获取用户信息by access_token and openId
    public static String url_get_user = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    public static WeChatUser getUserByAccessTokenAndOpenId(String access_token, String openid){
        String url = url_get_user.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
        JSONObject JSONUser = httpRequest(url, "GET", null);
        logger.info(JSONUser);

        if (JSONUser != null){
            if (JSONUser.getString("errcode") == null) {
                WeChatUser user = composeWeChatUser(JSONUser);
                logger.info(user);
                return user;
            }else{
                logger.error(JSONUser);
                logger.error("通过access_token="+access_token+", openid="+openid+" 没能获取微信用户信息.");
            }
        }
        logger.warn("NO Access Token Get!");
        return null;
    }

    // 组装一个WeChatUser
    public static WeChatUser composeWeChatUser(JSONObject jsonObject){

        WeChatUser user = new WeChatUser();

        user.setOpenid(jsonObject.getString("openid"));
        user.setNickname(jsonObject.getString("nickname"));
        user.setSex(jsonObject.getString("sex"));
        user.setProvince(jsonObject.getString("province"));
        user.setCity(jsonObject.getString("city"));
        user.setHeadImgUrl(jsonObject.getString("headimgurl"));
        user.setUnionid(jsonObject.getString("unionid"));
        user.setSubscribe(jsonObject.getString("subscribe"));
        user.setSubscribe_time(jsonObject.getString("subscribe_time"));
        user.setLanguage(jsonObject.getString("language"));

        return user;
    }
    public static OAuthInfo composeAuthInfo(JSONObject jsonObject){
        OAuthInfo authInfo = new OAuthInfo();
        authInfo.setAccess_token(jsonObject.getString("access_token"));
        authInfo.setExpires_in(jsonObject.getString("expires_in"));
        authInfo.setOpenid(jsonObject.getString("openid"));
        authInfo.setRefresh_token(jsonObject.getString("refresh_token"));
        authInfo.setScope(jsonObject.getString("scope"));
        authInfo.setUnionid(jsonObject.getString("unionid"));
        return authInfo;
    }


    //微信二维码登陆
    public static void wxLoginUrl(HttpServletRequest request,
                                  HttpServletResponse response){
        try {
            String url = scan_auth_url
                    .replace("APPID", APPID_URL)
                    .replace("REDIRECT_URI", URLEncoder.encode(WeChatConstants.CALL_BACK, "utf-8"))
                    .replace("STATE", "SCAN");
            logger.info("URL=:" + url);
            response.sendRedirect(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}