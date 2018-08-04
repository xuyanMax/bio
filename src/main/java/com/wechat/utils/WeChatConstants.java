package com.wechat.utils;

public class WeChatConstants {
    private static String ACCESS_TOKEN = "brbxyxzyz";
    //服务号应用
    public static final String appID = "wxb92b6517e66c5eda";
    public static final String appSecret = "7953a4803072b35c8e41ed27933f0ecb";
    //网页应用
    private static String APPID_URL = "wx73e0725a818a8ccb";
    private static String SECRET_URL = "570d28bcda358b8c8d7021e8ee18f184";

    //获取access_token的URI
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 用于进行当点击按钮的时候，能够在网页授权之后获取到code，再跳转到自己设定的一个URL路径上的接口，这个主要是为了获取之后于
     * 获取openId的接口相结合
     * 注意：参数REDIRECT_URI 表示的是当授权成功后，跳转到的自己设定的页面，所以这个要根据自己的需要进行修改
     */
    public static String Get_WEIXINPAGE_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?"+ "appid=APPID&redirect_uri="
            + "REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";

    public static String REDIRECT_URI = "http://population.chgc.sh.cn/user/info";

    // 通过扫描微信二维码登陆
    public static String scan_auth_url = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    //用于获取当前与微信公众号交互的用户信息的接口（一般是用第一个接口地址）
    private static String get_openId_url_msg = "https://api.weixin.qq.com/cgi-bin/user/info?" +
            "access_token=ACCESS_TOKEN&" +
            "openid=OPENID&lang=zh_CN";

    //在创建自定义菜单时指定URL为网页授权接口：
    public static final String url_snsapi_base = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=APPID&" +
            "redirect_uri=REDIRECT_URI&" +
            "response_type=code&" +
            "scope=snsapi_base&" +
            "state=s";
    //用于进行网页授权验证的接口URL，通过这个才可以得到opendID等字段信息
    public static String GET_WEBAUTH_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APP_SECRET&code=CODE&grant_type=authorization_code";

    // OAuth2 授权获取用户信息，通过两个参数 access_token and openId
    public static String GET_WECHAT_USER_URI = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
}
