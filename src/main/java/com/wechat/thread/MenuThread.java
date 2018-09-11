package com.wechat.thread;

import com.wechat.model.*;
import com.wechat.model.button.*;
import com.wechat.utils.AccessTokenUtil;
import com.wechat.utils.WeChatConstants;
import com.wechat.utils.WeChatUtils;
import org.apache.log4j.Logger;
import sun.tools.jstat.Token;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MenuThread implements Runnable {
    private static Logger logger = Logger.getLogger(MenuThread.class);
    @Override
    public void run() {
        if (TokenThread.access_token.getToken() != null){
            int result = WeChatUtils.createMenu(getMenu(), TokenThread.access_token.getToken());
            if(result == 0) {
                logger.info("Menu created successfully");
            }
            else {
                logger.warn("Menu failed to create, error code: " + result);
            }
        }else {
            logger.error("Access Token did not get");
        }
    }
    public static Menu getMenu(){
        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        ViewButton left = new ViewButton();
        left.setType("view");
        left.setName("中心简介");
        left.setUrl("http://www.chgc.sh.cn/page60");

        ViewButton center = new ViewButton();
        center.setName("职业人群项目");
        center.setType("view");
        try {
            center.setUrl(WeChatConstants.Get_WEIXINPAGE_Code
                    .replace("REDIRECT_URI", URLEncoder.encode(WeChatConstants.CALL_BACK, "utf-8"))
                    .replace("APPID", TokenThread.appID)
                    .replace("STATE", "AUTH"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ParentButton parentCenter = new ParentButton();
        parentCenter.setName("科研合作");
        parentCenter.setSub_button(new Button[]{center});

        ViewButton right = new ViewButton();
        right.setName("新闻动态");
        right.setUrl("http://www.chgc.sh.cn/page104");
        right.setType("view");

        menu.setButton(new Button[] {left, parentCenter, right});

        return menu;
    }
}
