README
================
该文件记录开发过程中的部分配置文件和测试方法。


|Author|Bright, Xu, zy (Alphabetical order)|
|---|---
|Email|xuyanpeter0619@gmail.com

****
## 目录

* [Project Description](#project)
* [JDK version](#jdk)
* [Tomcat version](#Tomcat)
* [db settings](#Database)
* [Sign-in](#signin)


Project
------
    Flup一期开发
    1. week 1
        1. Set up SpringMVC+Spring+MyBatis   
        2. Admin user insert personal data into db
        3. Admin user upload Excel(2003 version) files to server and download all user data.
        4. Connect remote MySQL db through ssh
    2. week 2
        1. User/admin signin
        2. Admin authentication 
        3. Download after uploading file(s). 
        4. Switch between local db and remote db      
    3. week 3
        1. Scan Wechat 2d barcode
       

jdk
------
    1.8.0_101
Tomcat
------
    9.0.46
Database
------
    db测试，连接本地数据库，不对远程数据库进行操作。
    连接本地127.0.0.1，需要:
    1. 对`src/main/resources/jdbc.properties`文件中，注释掉远程数据库的连接信息
    2. 对`src/main/java/com/bio/Utils/MyContextListener.java`，按代码提示，注释掉`@WebListener`
    
    反之，连接远程数据库，需要:
     1. 对`src/main/resources/jdbc.properties`文件中，释掉本地数据库的连接信息，反注释远程连接信息
     2. 对`src/main/java/com/bio/Utils/MyContextListener.java`，反注释`@WebListener`
    
   
signin
------
    用户/管理员登陆，校验姓名+身份证号，判断是否为Admin user，
    * 若是Admin user, 进入Admin主页index.jsp
    * 若是普通user，进入用户界面 xxx.jsp 
    