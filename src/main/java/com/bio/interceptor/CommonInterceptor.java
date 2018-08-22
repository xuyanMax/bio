package com.bio.interceptor;

import org.apache.log4j.Logger;
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
    private static Logger logger = Logger.getLogger(CommonInterceptor.class);

    private static String[] INTERCEPTOR_URL= {
            "/home",
            "/manualInsertion",
            "/auth/logout",
            "signup",
            "/uploadMultiFiles",
            "/admin/manualInsertion",
            "/admin/update",
            "/displayUses"
    };
//    https://blog.csdn.net/tonytfjing/article/details/39207551
//    https://blog.csdn.net/huangjp_hz/article/details/73614314
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String url = httpServletRequest.getRequestURL().toString();
        String username = (String) httpServletRequest.getSession().getAttribute(USERNAME);

        logger.info("=====interceptor begins======");
        logger.info("username = " + username);
        logger.info("snAdmin = "+ (String) httpServletRequest.getSession().getAttribute("snAdmin"));

        if (url.contains("logout")){
            if (username != null){
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
                logger.info("=====interceptor ends======");
                return false;
            }else
                return true;
        }
         /*进入login页面，判断session中是否有key，有的话重定向到首页，否则进入登录界面*/
        if (url.contains("login")){
            if (username != null) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/home");
                logger.info("=====interceptor ends======");
                return false;
            } else
                return true;
        }
        //todo: contains idcode
//        if (url.contains("signupPageFollowed")) {
//            if (httpServletRequest.getSession().getAttribute("idcode") == null ){
////                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/wx/login");
//                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/signupPage");
//                logger.info("=====interceptor end======");
//                return false;
//            }else
//                return true;
//        }
//        if (url.contains("signupPage")){
//            if (httpServletRequest.getSession().getAttribute("wxuser") == null){
//                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/wx/login");
//                logger.info("=====interceptor end======");
//                return false;
//            }else return true;
//        }
        //其他情况判断session中是否有key，有的话继续用户的操作
        if (username != null)
            return true;

        //最后的情况就是进入登录页面
        logger.info("=====interceptor end======");
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
