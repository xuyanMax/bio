package com.bio.service;

import com.wechat.utils.MessageUtil;
import com.wechat.model.TextMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
@Service
public class WeCharServiceImpl implements IWeChatService {

    // 处理微信发来的请求, 自动回复
    @Override
    public String wechatPost(HttpServletRequest request) {
        String respMsg = null;
        // xml请求解析
        Map<String, String> requestMap = null;
        try {
            requestMap = MessageUtil.xmlToMap(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        // 消息内容
        String content = requestMap.get("Content");

//        LOGGER.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);

        // 文本消息
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            //这里根据关键字执行相应的逻辑
            if(content.equals("xxx")){

            }

            //自动回复
            TextMessage text = new TextMessage();
            text.setContent("the text is" + content);
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime() + "");
            text.setMsgType(msgType);

            respMsg = MessageUtil.textMessageToXml(text);


        } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
            String eventType = requestMap.get("Event");// 事件类型
            // 订阅
            if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                TextMessage text = new TextMessage();
                text.setContent("欢迎关注，xxx");
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime() + "");
                text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                respMsg = MessageUtil.textMessageToXml(text);
            }
        }
        return respMsg;

    }
}
