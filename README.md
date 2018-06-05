README
================
该文件记录开发过程中的部分配置文件和测试方法。


|Author|Bright, Xu, zy
|---|---
|Email|xuyanpeter0619@gmail.com

****
## 目录

* [Project Description](#project)
* [JDK Version](#jdk)
* [Tomcat Version](#tomcat)
* [db Settings](#Database)
* [Sign-In](#signin)
* [开发遇到的问题交流](#Issues)


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
        2. Admin user's authentication and authorization 
        3. Download after uploading file(s). 
        4. Switch between local db and remote db      
    3. week 3
       
       

jdk
------
    1.8.0_101
tomcat
------
    9.0.46
Database
------
    db测试，连接本地数据库，不对远程数据库进行操作。
    连接本地127.0.0.1，需要:
    1. 对src/main/resources/jdbc.properties文件中，注释掉远程数据库的连接信息
    2. 对src/main/java/com/bio/Utils/MyContextListener.java，按代码提示，注释掉@WebListener
    
    反之，连接远程数据库，需要:
     1. 对src/main/resources/jdbc.properties文件中，释掉本地数据库的连接信息，反注释远程连接信息
     2. 对src/main/java/com/bio/Utils/MyContextListener.java，反注释@WebListener
    
   
signin
------
    用户/管理员登陆，校验姓名+身份证号，判断是否为Admin user，
    * 若是Admin user, 进入Admin主页index.jsp
    * 若是普通user，进入用户界面 xxx.jsp
------    
    当前登陆流程
    1. 进入首页登陆界面 url输入: **/questionaire/login，输入用户名+身份证组合验证是否为管理员
        a.如果是管理员，进入对应url/home页面，可上传文件、添加用户信息
        b.非管理员，暂定进入一个临时界面
    2. 上传文件功能，限定上传文件为2003版本的excel文件，上传文件后，解析文件中用户数据信息，并结合数据库中
       已存在用户信息[(#疑问1)]，生成下载队列成员信息表YYYmmDD.xls文件，到系统的Downloads目录下
            
Issues
------
    1. 根据上传文件，生成下载队列信息表中可包含上传文件中的原身份证号信息。然后结合的数据库数据中，无法反推得到
       该部分用户原身份证号信息[从实现的角度，还是有点疑惑]
------
    2. 项目中对SpringMVC框架中Session的保持和前端页面与Controller间的传递，以及用户认证和权限应用接触不多，
       代码规范及框架使用中存在问题请指点
