package com.bio.controller.token;

import com.wechat.thread.MenuThread;
import com.wechat.thread.TokenThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
//references
//https://blog.csdn.net/qq_16071145/article/details/51366582
//https://blog.csdn.net/qq_33429968/article/details/72918307
public class GetAccessTokenServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        Thread token = new Thread(new TokenThread());
        token.setDaemon(true);
        Thread menu = new Thread(new MenuThread());
        token.setDaemon(true);
        token.start();
        menu.start();
    }
}
