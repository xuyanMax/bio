package com.wechat.thread;

import com.wechat.model.*;
import com.wechat.model.button.*;
import com.wechat.utils.AccessTokenUtil;
import com.wechat.utils.WeChatUtils;
import org.apache.log4j.Logger;

public class MenuThread implements Runnable {
    private static final String appID = "wxb92b6517e66c5eda";//wx0f81f68f813bd68d
    private static final String appSecret = "7953a4803072b35c8e41ed27933f0ecb";//c9d7f54ec1d0642d187141636ba69af2
    private static AccessToken access_token=null;
    private static Logger logger = Logger.getLogger(MenuThread.class);
    @Override
    public void run() {
        access_token = AccessTokenUtil.getAccessToken(appID, appSecret);
        if (access_token.getToken() != null){
            int result = WeChatUtils.createMenu(getMenu(), access_token.getToken());
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
        center.setName("科研合作Flup");
        center.setType("view");
        center.setUrl("http://population.chgc.sh.cn");

        ViewButton right = new ViewButton();
        right.setName("新闻动态");
        right.setUrl("http://www.chgc.sh.cn/page104");
        right.setType("view");

        menu.setButton(new Button[] {left, center, right});

        return menu;
    }
}
