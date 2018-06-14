package com.bio.Utils;

import com.jcraft.jsch.JSchException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// reference: https://stackoverflow.com/questions/33526427/spring-boot-ssh-mysql

/*若需要切换到本地数据库，请注释掉下面一行 @WebListener, 否则去掉注释*/
//@WebListener
public class MyContextListener implements ServletContextListener {
    private SSHConnection sshConnection;

    public MyContextListener() {
        super();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("=====Context initialized====");
        System.out.println("如果为本地数据库测试，请注释掉@WebListener");
        try {
            sshConnection = new SSHConnection(); // make a connection
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("=====Context destroyed=====");
        sshConnection.closeSSH();// disconnect
    }
}
