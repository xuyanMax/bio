README
================
该文件记录开发过程中的部分配置文件和测试方法。


Author|Bright, Xu, zcy, zy 
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
* [问题交流区](#issues)


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
4. week 4
    1. `xu`注册个人订阅号(用于熟悉微信接口功能)/企业服务号(申请中)
        1. 订阅号
            1. `appId=wx0f81f68f813bd68d`
            1. `secret=c9d7f54ec1d0642d187141636ba6XXXX`
    2. `zy`更新`数据库表头说明`，并更新`jdbc.properties`
    3. 更新用户登陆逻辑(添加本地管理员/系统管理员)
    
5. week 5
    1. 微信接口接口配置 `/wx/token/get`
    1. 实现获取网页授权的接口`Access_Token`的类
        1. `TokenThread`, `AccessTokenUtil`, `AccessToken`
    1. 消息及消息处理工具的封装 ``
    1. 使用`微信公众平台接口调试工具`
        1. 在`微信公众平台->基本配置->ip白名单`添加当前主机所在ip
        1. 在`微信公众平台->基本配置->服务器配置`更改服务器地址(ngrok随机生成公网地址)
        1. 前往`微信公众平台->开发者工具->在线接口测试工具`调试
    1. 使用`测试号管理`
        1. 修改`接口配置信息`
        1. 修改`JS接口安全域名`       
    1. 熟悉`微信公众平台接口调试工具`
        1. 不需要修改`ngrok`生成的临时`url`
        1. 测试的`接口类型`包括:
            1. 测试成功`基础支持`
                1. 需要指定生成的随机`url`
            1. 测试成功`消息接口测试`
                1. 需要指定生成的随机`url`
                1. 测试文本、图片、语音等消息
            1. 测试成功`自定义菜单栏`
                1. 输入有效的`access_token`
                1. 输入`json数据`
                1. 返回`{"errcode":48001,"errmsg":"api unauthorized hint: [GffX3a0146vr61!]`，说明调用api权限不够
            1. 测试`用户管理`
            1. 测试`向用户发送信息`
    1. `bright`申请小程序并添加开发者
       1. `appId=wx5410ab8c5094da60`
       1. 调试`微信开发者工具`

      
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
-----
db测试，连接本地数据库，不对远程数据库进行操作。
连接本地127.0.0.1，需要:
1. 对`src/main/resources/jdbc.properties`文件中，注释掉远程数据库的连接信息
2. 对`src/main/java/com/bio/Utils/MyContextListener.java`，按代码提示，注释掉`//@WebListener`

反之，连接远程数据库，需要:
1. 对`src/main/resources/jdbc.properties`文件中，释掉本地数据库的连接信息，反注释远程连接信息
2. 对`src/main/java/com/bio/Utils/MyContextListener.java`，去除注释`@WebListener`
   
 
signin
------
    
管理员利用md5脚本工具`Flup/scripts/output_file_md5.py`，预先添加persons表中的管理员用户，对应的centerid字段不能为空且要出现于表centers的idcenter字段中
     
------
1. 用户/本地管理员/系统管理员登陆，校验姓名+身份证号，判断是否为Admin user，
    1. 若是本地管理员, 进入主页index.jsp
    1. 若是普通user，进入待定用户界面 xxx.jsp
    1. 若是系统管理员, 进入待定系统界面 yyy.jsp
------    
2. 当前登陆流程
    1. 判断无指定session, 则拦截访问, 跳转至登陆界面: 请求输入用户名+身份证验证
        1. 管理员，可上传文件、添加用户信息  
        1. 普通用户  
        1. 系统管理员
     
upload
-------

**上传文件版本及格式要求**
1. 限定上传文件格式: Microsoft Excel 2003
2. 为方便解析文件中用户数据，文件数据格式有一定要求，需要严格按照提供的上传队列信息表中前2行为字段声明，最后6行为字段说明，中间部分为有用
的待插入用户数据格式，且无空数据行
3. 上传文件后，解析用户数据信息，并结合数据库中已存在用户信息(疑问1)，生成`下载队列成员信息表YYYmmDD.xls`文件，并下载到系统的`Downloads`
    
    
issues
------
**开发问题及解决方案**
1. 根据上传文件，`生成下载队列信息表`中可包含上传文件中的原身份证号信息，然后结合的数据库数据中，无法反推得到该部分用户原身份证号信息(从实现的角度，还是有点疑惑)
1. `文件下载`，并未像传统下载方式一样，在浏览器界面左下角显示下载文件，而是直接下载到当前用户的Downloads，同时在浏览器界面提示下载成功信息
1. 身份证在本单位要查重
1. `Session`管理，目前使用注解@sessionAttribute
1. `ModelAndView`默认forward, redirect问题
    1. forward为默认, 并结合视图解析器的前缀(`/WEB-INF/`)与后缀信息(`.jsp`)
    2. redirect需要特别指定
        1. 完整写出对应项目的根路径, 如`/WEB-INF/views/success.jsp`
        2. 重定向到`Controller`
1. `权限管理`方法, 目前使用拦截器拦截未登陆用户进入指定页面，未实现系统权限管理. 可选Spring Shiro 或 Spring Security框架
1. `Bootstrap`前端页面引用路径不识别问题
1. `Tomcat部署war包`访问路径问题，目前通过替换ROOT文件方式实现
1. `微信公众号本地开发80端口映射解决方案`: 
    1. 下载ngrok, 地址 [https://ngrok.com/download](https://ngrok.com/download)
    1. 点击注册并登陆[ngrok](https://dashboard.ngrok.com/user/signup)
    1. 获取授权码 `yourauthtoken`
    1. 输入ngrok授权码 `${ngrok}/./ngrok authtoken yourauthtoken`
    1. 启用端口映射(注: http后面跟的是本地要映射的端口, 如8080) `./ngrok http 8080`
    1. 弊端
        1. 域名随机
        1. 服务器在国外，访问速度慢
    2. 优势
        1. 提供公网80端口到内网任意端口的映射机制
        2. 遍于测试
1. 微信公众测试号, 接口配置`token`验证,涉及请求参数`token`, `timestamp`, `nonce`, `signature`
    1. `checkTokenUtils`和`@Controller WeChatToken`
1. 微信`Access_Token`获取方法
    1. IP白名单添加开发者ip, 否则无法获得Access_Token
    1. 配置TokenThread，轮训获得Access_Token
    1. 配置GetAccessTokenServlet, 于web.xml添加Servlet, 声明其在Spring容器启动时，启动该Servlet中线程，获取Access_Token 
1.  获取用户openId的途径有 [参考](https://www.cnblogs.com/txw1958/p/weixin76-user-info.html)
    1. 用户关注以及回复消息的时候，均可以获得用户的OpenID
    1. 通过OAuth2.0方式弹出授权页面获得用户基本信息
    1. 通过OAuth2.0方式不弹出授权页面获得用户基本信息

    
   
