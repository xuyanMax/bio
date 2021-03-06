package com.bio.Utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;

public class SSHConnection {
    public static final String SSH_USERNAME = "user20181";
    public static final String SSH_PASSWORD = "#user*2018";
    public static final String SSH_HOST = "202.127.7.29";
    public static final String DB_USERNAME = "user20182";
    public static final String DB_PASSWORD = "!user;2018";
    public static final String DB_HOST = "127.0.0.1";
    public static final int DB_REMOTE_PORT = 3306;
    public static final int DB_LOCAL_PORT = 3306;
    public static final int REMOTE_SSH_PORT = 10061;
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/cdcDev";
    private static Logger logger = Logger.getLogger(SSHConnection.class);

    private Session session;

    // open connection
    public SSHConnection() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(SSH_USERNAME, SSH_HOST, REMOTE_SSH_PORT);
        session.setPassword(SSH_PASSWORD);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();//ssh connection established!
        //这里打印SSH服务器版本信息
        logger.info(session.getServerVersion());
        int assigned_port = session.setPortForwardingL(DB_LOCAL_PORT, DB_HOST, DB_REMOTE_PORT);//3306
    }

    // close connection
    public void closeSSH() {
        session.disconnect();
    }
}
