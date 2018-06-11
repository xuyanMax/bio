README
================
该文件记录开发过程中的部分配置文件和测试方法。


Author|Bright, Xu, zy
:---:|:---:
Email|xuyanpeter0619@gmail.com

****
## 目录

* [项目进展](#project)
* [JDK版本](#jdk)
* [Tomcat配置](#tomcat)
* [DB配置](#database)
* [登陆说明](#signin)
* [上传文件](#upload)
* [开发遇到的问题交流](#issues)


Project
------
**Flup一期开发**
1. week 1
    1. 搭建SpringMVC+Spring+MyBatis，两个关键配置文件 `springmvc.xml, spring-mybatis.xml`
    2. 管理员用户功能部分实现，包括手动输入用户信息+上传Excel文件后下载队列成员信息表
    4. 实现ssh连接远程数据库 `utils/DBUtils`
2. week 2
    1. 实现管理员用户登陆
    2. 实现管理员登陆`认证(authentication)`并在研究用户`权限(authorization)`使用
    3. 实现上管理员上传、下载`成员队列信息表`功能 
    4. 完成远程/本地数据库切换工作及操作说明，详情参考目录`db settings`      
3. week 3
    1. 实现服务器Tomcat部署项目war包的工作，详情参考目录`tomcat`
    2. md5生成器脚本工具
    3. 微信注册和登陆机制研究
       
       

jdk
------
    版本号1.8.0_101
    
tomcat
------
1. 版本号9.0.46
    
2. **服务器 _tomcat_ 部署 _questionaire.war_ 步骤**:
    1. 将编译好的war包或码云Flup项目下的`bio/target/questionaire.war`包拷贝到服务器`/home/chgc/apache-tomcat-9.0.8/webapps/`下, 运行中的tomcat会将`questionaire.war`自动编译生成`questionaire`文件目录    
      `scp -P 10061  ~/tmp/questionaire.war chgc@202.127.7.29:/home/chgc/apache-tomcat-9.0.8/webapps/`
    2. 删除当前目录`~/apache-tomcat-9.0.8/webapps/`下的`ROOT`文件目录 `rm -r ROOT`
    3. 重命名新生成的`questionaire`, 用来替换原来的`ROOT`, `mv questionaire ROOT`
    4. 关闭并重启 _tomcat_ 服务器 `.\startup.sh` 
    
database

db测试，连接本地数据库，不对远程数据库进行操作。
连接本地127.0.0.1，需要:
1. 对src/main/resources/jdbc.properties文件中，注释掉远程数据库的连接信息
2. 对src/main/java/com/bio/Utils/MyContextListener.java，按代码提示，注释掉@WebListener
    
反之，连接远程数据库，需要:
1. 对src/main/resources/jdbc.properties文件中，释掉本地数据库的连接信息，反注释远程连接信息
2. 对src/main/java/com/bio/Utils/MyContextListener.java，去除注释//@WebListener
   
signin
------
    
管理员利用md5生成工具，预先添加persons表中的管理员用户，对应的centerid字段不能为空且要出现于表centers的idcenter字段中
     
------
    用户/管理员登陆，校验姓名+身份证号，判断是否为Admin user，
    * 若是Admin user, 进入Admin主页index.jsp
    * 若是普通user，进入待定用户界面 xxx.jsp
------    
    当前登陆流程
    1. 进入首页登陆界面 url输入: **/questionaire/login，输入用户名+身份证组合验证是否为管理员
        a.如果是管理员，进入对应url/home页面，可上传文件、添加用户信息
        b.非管理员，暂定进入一个临时界面
    2. 上传文件功能，限定上传文件为2003版本的excel文件，上传文件后，解析文件中用户数据信息，并结合数据库中
       已存在用户信息[(#疑问1)]，生成下载队列成员信息表YYYmmDD.xls文件，到系统的Downloads目录下
     
upload
-------


**上传文件版本及格式要求**
1. Excel 2003
2. 为方便解析文件中用户数据，文件数据格式有一定要求，需要严格按照提供的上传队列信息表中前2行为字段声明，最后6行为字段说明，中间部分为有用
的待插入用户数据格式，且无空数据行
    
    
issues
------
开发遇到的问题及现行解决方案
1. 根据上传文件，生成下载队列信息表中可包含上传文件中的原身份证号信息。然后结合的数据库数据中，无法反推得到该部分用户原身份证号信息[从实现的角度，还是有点疑惑]
1. Session管理方法，目前使用注解@sessionAttribute
1. ModelAndView默认forward, redirect问题
1. 权限管理方法, 目前使用拦截器拦截未登陆用户进入指定页面，未实现不权限管理. 可选Spring Shiro and Spring Security框架
1. Bootstrap前端页面引用路径不识别问题
1. Tomcat部署war包访问路径问题，目前通过替换ROOT文件方式实现
1. 文件下载后，并未像传统下载方式一样，在浏览器界面左下角显示下载文件，而是直接下载到用户的Downloads文件夹内，同时在浏览器界面提示下载成功信息
    
    
    
   
