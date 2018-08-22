package com.bio.Utils;

import com.jcraft.jsch.JSchException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// reference: https://stackoverflow.com/questions/33526427/spring-boot-ssh-mysql
/*若需要切换到本地数据库，请注释掉下面一行 @WebListener, 否则去掉注释*/
//@WebListener
public class MyContextListener implements ServletContextListener {
    private SSHConnection sshConnection;
    private Logger logger = Logger.getLogger(MyContextListener.class);
    public MyContextListener() {
        super();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("=====Context initialized====");
        logger.warn("如果测试本地数据库，请注释掉@WebListener");
        try {
            sshConnection = new SSHConnection(); // make a connection
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("=====Context destroyed=====");
        sshConnection.closeSSH();// disconnect
    }
}
