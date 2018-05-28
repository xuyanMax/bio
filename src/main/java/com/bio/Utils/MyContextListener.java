package com.bio.Utils;

import com.jcraft.jsch.JSchException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// refer: https://stackoverflow.com/questions/33526427/spring-boot-ssh-mysql
@WebListener
public class MyContextListener implements ServletContextListener {
    private SSHConnection sshConnection;

    public MyContextListener() {
        super();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("=====Context initialized====");
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
