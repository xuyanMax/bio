package com.wechat.utils;

import com.bio.beans.WeChatUser;
import com.wechat.model.message.response.Article;
import com.wechat.model.message.response.NewsMessage;
import com.wechat.model.message.response.TextMessage;
import com.wechat.thread.TokenThread;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 核心服务类
 * 微信公众帐号开发教程第14篇-自定义菜单的创建及菜单事件响应
 * reference: https://blog.csdn.net/lyq8479/article/details/8952173
 */
public class CoreService {
    private static Logger logger = Logger.getLogger(CoreService.class);
    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析x
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            /*
            *
            * 获得openId, 获取用户信息
            * */
            WeChatUser user = WeChatUtils.getWeChatUser(fromUserName, AccessTokenUtil.getAccessToken(TokenThread.appID, TokenThread.appSecret).getToken());
            logger.info("通过回复信息获取到的用户是: " + user);

            // 文本消息
            List<Article> articles = new ArrayList<>();
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "您发送的是文本消息！";
                String content = requestMap.get("Content");

                // 创建图文消息
                NewsMessage newsMessage = new NewsMessage();
                newsMessage.setToUserName(fromUserName);
                newsMessage.setFromUserName(toUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                newsMessage.setFuncFlag(0);

                //单图文消息
                if (content.equals("1")){
                    Article article = new Article();
                    article.setTitle("测试");
                    article.setDescription("务正业");
                    article.setPicUrl("http://population.chgc.sh.cn/images/login.png");
                    article.setUrl("http://population.chgc.sh.cn/login");
                    articles.add(article);
                    // 设置图文消息个数
                    newsMessage.setArticleCount(articles.size());
                    // 设置图文消息包含的图文集合
                    newsMessage.setArticles(articles);
                    // 将图文消息对象转换成xml字符串
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    return respMessage;

                } else if (content.equals("2")){
                    Article article = new Article();
                    article.setTitle("没什么");
                    // 图文消息中可以使用QQ表情、符号表情
                    article.setDescription("Flup -> Puss");
                    // 将图片置为空
                    article.setPicUrl("");
                    article.setUrl("http://population.chgc.sh.cn/login");
                    articles.add(article);
                    newsMessage.setArticleCount(articles.size());
                    newsMessage.setArticles(articles);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    return respMessage;
                }// 多图文消息
                else if ("3".equals(content)) {
                    Article article1 = new Article();
                    article1.setTitle("好说的");
                    article1.setDescription("");
                    article1.setPicUrl("http://population.chgc.sh.cn/images/login.png");
                    article1.setUrl("http://population.chgc.sh.cn//login");

                    Article article2 = new Article();
                    article2.setTitle("事情");
                    article2.setDescription("");
                    article2.setPicUrl("http://57792978.ngrok.io/images/lucky.png");
                    article2.setUrl("http://57792978.ngrok.io/login");

                    Article article3 = new Article();
                    article3.setTitle("告诉你");
                    article3.setDescription("");
                    article3.setPicUrl("http://population.chgc.sh.cn/images/lucky.png");
                    article3.setUrl("http://population.chgc.sh.cn/login");

                    articles.add(article1);
                    articles.add(article2);
                    articles.add(article3);
                    newsMessage.setArticleCount(articles.size());
                    newsMessage.setArticles(articles);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    return respMessage;
                } else if (content.equals("Flup")){
                    //reference: https://blog.csdn.net/lyq8479/article/details/9393195
                    String url = WeChatUtils.url_snsapi_userinfo.replace("REDIRECT_URL", WeChatUtils.REDIRECT_URL);
                    url = url.replace("APPID", TokenThread.appID);
                    logger.info("Redirect url " + url);
                    textMessage.setContent("欢迎访问<a href=\"" + url + "\">Flup</a>!");

                    // 将文本消息对象转换成xml字符串
                    // 默认回复消息
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                    return respMessage;
                }

            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                logger.info("事件推送");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 自定义菜单权没有开放，暂不处理该类消息
                }
            }
            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return respMessage;
    }
}
