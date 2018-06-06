package com.bio.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//todo: update preHandle logic
//reference: https://blog.csdn.net/qwe6112071/article/details/51099437
public class CommonInterceptor implements HandlerInterceptor {
    /*session attributes*/
    private static final String USERNAME = "username";
    private static final String USER = "user";

    private static String[] INTERCEPTOR_URL= {
            "/home",
            "/manualInsertion",
            "/auth/logout",
            "signup",
            "/uploadMultiFiles",
            "/admin/manualInsertion",
            "/admin/update",
            "/displayUses"
    };//todo: 唯一表示管理员的session id
//    https://blog.csdn.net/tonytfjing/article/details/39207551
//    https://blog.csdn.net/huangjp_hz/article/details/73614314
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURL().toString();
        String username = (String) httpServletRequest.getSession().getAttribute(USERNAME);
        System.out.println("inside prehandle");
        // 进入login页面，判断session中是否有key，有的话重定向到首页，否则进入登录界面
        if (url.contains("login")){
            if (username != null) {
                System.out.println("inside login");
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            }
            else
                return true;//继续登陆请求
        }
        // 其他情况判断session中是否有key，有的话继续用户的操作
//        if (username == null) {
//            return true;//继续当前页面
//        }
        //最后就是进入登陆页面
        System.out.println("inside login last");
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
        return false;//重定向
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
