README
================
该文件记录开发过程中的部分配置文件和测试方法。


Author|Bright, Xu, xjq, zcy, zy 
:---:|:---:
Email|xuyanpeter0619@gmail.com

****
## 目录

* [项目进展](#项目)
* [JDK版本](#jdk)
* [maven版本](#maven)
* [Tomcat配置](#tomcat)
* [DB配置](#数据库)
* [ssh连接配置](#ssh连接)
* [登陆说明](#登陆)
* [上传文件](#上传文件)
* [问题交流区](#问题交流)


项目
------
### Flup一期开发

**week1**  
1. 搭建SpringMVC+Spring+MyBatis，两个关键配置文件 `springmvc.xml, spring-mybatis.xml`
2. 管理员用户功能部分实现，包括手动输入用户信息+上传Excel文件后下载队列成员信息表
3. 实现ssh连接远程数据库 `utils/DBUtils`

**week 2**
1. 实现管理员用户登陆
2. 实现管理员登陆`认证(authentication)`并在研究用户`权限(authorization)`使用
3. 实现上管理员上传、下载`成员队列信息表`功能 
4. 完成远程/本地数据库切换工作及操作说明，详情参考目录`db settings`      

**week 3**
1. 实现服务器Tomcat部署项目war包的工作，详情参考目录`tomcat`
2. md5生成器脚本工具
3. 微信注册和登陆机制研究

**week 4**
1. `xu`注册个人订阅号(用于熟悉微信接口功能)/`Bright`企业服务号(申请中)
    1. 订阅号
        1. `appId=wx0f81f68f813bd68d`
        1. `secret=c9d7f54ec1d0642d187141636ba6XXXX`
    1. 服务号
        1. `appid = wxb92b6517e66c5eda`
        1. `secret = 7953a4803072b35c8e41ed27933f****`
2. `zy`更新`数据库表头说明`，并更新`jdbc.properties`
3. 更新用户登陆逻辑(添加本地管理员/系统管理员)
    
**week 5**
1. 微信接口接口配置 `/wx/token/get`
1. 实现获取网页授权的接口`Access_Token`的类
    1. `TokenThread`, `AccessTokenUtil`, `AccessToken`
1. 消息及消息处理工具的封装
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
    1. 获取用户信息
1. `bright`申请小程序并添加开发者
   1. `appId=wx5410ab8c5094da60`
   1. 调试`微信开发者工具`
1. `zy`添加新需求，调查问卷页面
    1. 利用Javascript实现调查问卷页面
1. 添加测试用户:
    1. 姓名: 本地管理员
    2. 身份证号: 13010419920518241X

**Week 6**
1. `Bright`测试问题反馈
    1. 姓名、身份证号、单位内编号、条形码、身份为必填项，其余可缺省
    1. 上传页面添加一个`下载表格模块`，方便本地管理员下载有表头的空文件
    1. 逐个录入保存在Web前端，点击上传后全部并下载Excel文件
1. 微信端扫码登陆
1. 微信OAuth2.0授权登录，获取用户openId
    1. 第三方发起微信授权登录请求，微信用户允许授权第三方应用后，微信会拉起应用或重定向到第三方网站，并且带上授权临时票据code参数；
    2. 通过code参数加上AppID和AppSecret等，通过API换取access_token；
    3. 通过access_token进行接口调用，获取用户基本数据资源或帮助用户实现基本操作。
1. `zy`提出更新`下载表格模版`，非数据行以#开头，并标注单位内编号不能以#起头

**week 7**

1. 上传`MP_verify_...`
1. 更改公众号`appid`和`secret`
1. 将所有24道'填空题'的正则表达，传入数据库，生成txt文件，例如`您的出生地是_正则_（省/直辖市）#请输入正确的省/直辖市`
1. 生成问卷调查，使用了`https://surveyjs.io`
    1. 动态问卷，需要创建`JsonGenerator`，利用`fastjson`，创建符合`surveyjs`规范的JSON数据
    1. 完成`text`测试
    1. 接下来，测试
        1. `multitext`, `checkbox` with `Validators`

**week 8** 
1.  测试通过`JsonGenerator`，存在问题:`table`表格类型题目确定输入行数
    1. 最终解决方法，使用类型为`matrixdynamic`工具，动态插入不定行数的表格题

**week 10**
1. 微信公众号二维码扫码登陆
1. 修改`table`表格类，使用`matrixdynamic`组件，设置第一栏为dropdown，其余为input field
1. 二维码登陆权限申请成功

**week11**
1. 部署问卷页面`/user/survey`，从远程/本地数据库读取
1. __添加管理员注册功能__
    1. 关注公众号
    1. 从用户注册入口进入，输入姓名、身份证等信息
    1. 系统向预留的手机号发送验证码，输入验证码
    1. 绑定单位管理员个人微信
    1. 注册成功
1. __问卷调查__
    1. 规范题目中的正则表达式规则，以^开始$结束(^XXXX$).
    1. 处理用户提交的问卷答案信息
    1. 添加每一道题目的错误提示(根据正则判断错误后给出的提示): 在`question`中以`#text`形式表示
        1. 例题： `您的身份证号码为：_^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9x]$#请输入合法18位身份证号_`
    1. 数据库`options`列逗号半角、全角使用规则
        1. 例子：`否，未曾患良性乳腺疾病`,`乳腺增生`,`结节`,`导管扩张`,`良性纤维腺瘤`,`感染`,`囊肿`,`其他良性乳腺疾病`
        1. 如上，半角逗号用于隔断选项
    1. 数据库`question`列分号半角使用规则
        1. 半角(英文)用于分割`question`中的`blank`类多项填空题中的每一个问题.
    1. `多项选择题`中填23空名称的截取规则: 
        1. 填空名称(text)后为填空区域
        1. 填空名称可以在`question`列中，以`#填空名称#`的形式截取。当前并没有修改数据库，有待探讨。
    1. 表格`table`类型题目，第一列需要制作下拉菜单的，在`options`中表示表格首列名称前加一个字母`d`
        1. 例子:`关系（d父母、同父母的兄弟姐妹、子女）,癌肿,患癌时间（XX年）`
        1. 如上，dropdown列，用顿号隔开选项. 表格的不同列用半角逗号隔开选项
    1. 说明，什么是半角/全角？
        1. 半角为英文的标点符号
        1. 全角为中文的标点符号
1. __程序中添加服务器日志记录功能__ _done_
1. 测试上传服务器 _done_
1. 短信验证码登录注册功能实现 _done_
    1. 再加上使用cookie防止刷新页面导致倒计时失效的代码 _not started_

**week12**
1. 注册功能
    1. Ajax向后台发送六位随机数，存储在Session中，默认30分钟后过期
    1. 修复服务器log日志中文显示乱码
        1. 备份catalina.sh到~目录
        1. [参考](https://blog.csdn.net/guolongpu/article/details/53383362)
        1. 修改后，重启tomcat
1. 测试问卷，修改数据存储逻辑
1. 微信扫码登录功能
1. 



jdk
------

java version "1.8.0_101"

maven
------

OS name: "mac os x", version: "10.13.5", arch: "x86_64", family: "mac"

tomcat
-------
`版本号9.0.46`
    
**服务器 _tomcat_ 部署 _questionaire.war_ 步骤**:
1. 将编译好的war包或码云Flup项目下的`bio/target/questionaire.war`包拷贝到服务器`/home/chgc/apache-tomcat-9.0.8/webapps/`下, 运行中的tomcat会将`questionaire.war`自动编译生成`questionaire`文件目录，涉及指令如下  
1. `scp -P 10061  /Users/apple/Documents/workspace/bio/target/questionaire.war chgc@202.127.7.29:/home/chgc/apache-tomcat-9.0.8/webapps/`复制文件
2. `rm -rf questionaire.war`，待编译完成`questionaire`文件夹后，删除war包
3. `./shutdown.sh`关闭tomcat，关闭服务器
4. `rm -rf ROOT`，删除ROOT文件夹
5. `mv questionaire ROOT`，更名为ROOT
6. `./startup.sh`，重启服务器
    
数据库
-----
## 本地测试与远程数据库切换
db测试，连接本地数据库，不对远程数据库进行操作。
连接本地127.0.0.1，需要:
1. 对`src/main/resources/jdbc.properties`文件中，注释掉远程数据库的连接信息
2. 对`src/main/java/com/bio/Utils/MyContextListener.java`，按代码提示，注释掉`//@WebListener`

反之，连接远程数据库，需要:
1. 对`src/main/resources/jdbc.properties`，释掉本地数据库的连接信息，反注释远程连接信息
2. 对`src/main/java/com/bio/Utils/MyContextListener.java`，去除注释`@WebListener`


ssh连接
------

### 创建SSH连接
1. 添加`Jcraft` Libarary 到pom.xml
1. 创建一个`SSHConnection.java`类用于连接SSH服务器
1. 定义一个`MyContextListener.java`实现了ServletContextListener的监听器，当程序启动时，创建一个ssh连接，关闭时，关闭ssh连接
1. `jdbc.properties`文件中指定ssh服务器的端口号3306，并更新文件中数据库服务器用户名及密码

### 调查问卷
调查问卷功能涉及代码在`src/main/java/com/JsonGenerator`，调用第三方[surveyjs(https://surveyjs.io) 生成多种问卷题目，调用[fastJson](https://github.com/alibaba/fastjson) 生成可供`surveyjs`识别的`JSON`数据.
1. 本地测试
    测试FetchData.java下的main函数
1. 部署到服务器 


登陆
------
    
管理员利用md5脚本工具`Flup/scripts/output_file_md5.py`，预先添加persons表中的管理员用户，对应的centerid字段不能为空且要出现于表centers的idcenter字段中
     
## pc端登陆
1. 用户/本地管理员/系统管理员登陆，校验姓名+身份证号，判断是否为Admin user，
    1. 若是本地管理员, 进入主页index.jsp
    1. 若是普通user，进入待定用户界面 xxx.jsp
    1. 若是系统管理员, 进入待定系统界面 yyy.jsp
------    
2. 登陆流程
    1. 判断无指定session, 则拦截访问, 跳转至登陆界面: 请求输入用户名+身份证验证
        1. 管理员，可上传文件、添加用户信息  
        1. 普通用户  
        1. 系统管理员

### 微信端扫码登陆
     
上传文件
-------

**上传文件版本及格式要求**
1. 限定上传文件格式: Microsoft Excel 2003
2. 为方便解析文件中用户数据，文件数据格式有一定要求，需要严格按照提供的上传队列信息表中前2行为字段声明，最后6行为字段说明，中间部分为有用
的待插入用户数据格式，且无空数据行
3. 上传文件后，解析用户数据信息，并结合数据库中已存在用户信息，生成`下载队列成员信息表YYYYmmDD.xls`文件，并下载到系统的`~/Downloads/`

**下载表格模版**
进入`上传文件`页面，点击导航栏的`下载表格模版`,模版自动下载到`Downloads`文件夹下
    

问题交流
------
**上传/下载文件**

1. 根据上传文件，`生成下载队列信息表`中可包含上传文件中的原身份证号信息，然后结合的数据库数据中，无法反推得到该部分用户原身份证号信息(从实现的角度，还是有点疑惑)
1. `文件下载`，并未像传统下载方式一样，在浏览器界面左下角显示下载文件，而是直接下载到当前用户的Downloads，同时在浏览器界面提示下载成功信息

**SSH 远程连接**
参考`/com/bio/Utils/SSHConnection.java`

**tomcat部署**
1. `Tomcat部署war包`访问路径问题，通过替换ROOT文件方式实现


**session管理**
1. `Session`管理，目前使用注解@sessionAttribute
1. `ModelAndView`默认forward, redirect问题
    1. forward为默认, 并结合视图解析器的前缀(`/WEB-INF/`)与后缀信息(`.jsp`)
    2. redirect需要特别指定
        1. 完整写出对应项目的根路径, 如`/WEB-INF/views/success.jsp`
        2. 重定向到`Controller`

**权限管理**
1. `权限管理`方法, 目前使用拦截器拦截未登陆用户进入指定页面，未实现系统权限管理. 可选Spring Shiro 或 Spring Security框架

**404PageNotFound**
__Spring MVC对于url的匹配采用的是一种叫做“最精确匹配的方式”__
1. 定义一个拦截所有url的规则`@requestMapping("*")`，那么实际上不存在找不到的页面了，也就是永远不会进入noHandlerFound方法体内
1. 为别的请求都配置上@requestMapping, 那么请求过来，要么进入精确匹配的method（也就是找的到的），要么进入@requestMapping("*)拦截的方法体内（也就是找不到的), 那么我们只要让@requestMapping("*)拦截的这个方法返回一个自定义的404界面就OK了

**拦截器**

1. 拦截的`url`路径有:
    1. `/signup`, `/login`, `/logout`, `/admin`

**log4j说明**
1. 日志输出级别，由低到高 `ERROR < WARN < INFO < DEBUG`
    1. 如输出级别为`DEBUG`， 则全部输出
    1. 如输出级别为`INFO`， 则除了`DEBUG`全部输出


**静态数据的访问**
1. `Bootstrap`前端页面引用路径不识别问题
1. 静态图片的访问 image, `spring-mvc.xml`新增代码
    1. `<mvc:resources mapping="/images/**" location="/images/" />`
    1. `<mvc:annotation-driven />`


**微信公众号本地开发80端口映射解决方案**
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
    2. 便于测试

**微信公众测试号**
1. 微信公众测试号, 接口配置`token`验证,涉及请求参数`token`, `timestamp`, `nonce`, `signature`
    1. 微信`Access_Token`获取方法
        1. IP白名单添加开发者ip, 否则无法获得Access_Token
        1. 配置TokenThread，轮训获得Access_Token
        1. 配置GetAccessTokenServlet, 于web.xml添加Servlet, 声明其在Spring容器启动时，启动该Servlet中线程，获取Access_Token 

 
**微信公众平台接口调试工具**
1. 测试微信不同接口类型

**获取用户openId的途径有 [参考](https://www.cnblogs.com/txw1958/p/weixin76-user-info.html)**
1. 用户关注以及回复消息的时候，均可以获得用户的OpenID
1. 通过OAuth2.0方式弹出授权页面获得用户基本信息
1. 通过OAuth2.0方式不弹出授权页面获得用户基本信息

**向微信特定地址发起GET请求**

**SERVERE:Could not contact localhost:8005. Tomcat may not be running. Connection refused**

1. 运行./shutdown停止tomcat报错
    1. 可能是tomcat没完全开启就关闭，kill掉进程后重启
        1. netstat -aon
        1. kill -9 pid
    1. 也可能找到jdk的bug，找到`jdk1.8.xx` 的安装路径，修改其子目录 /jre/lib/security/ 下的 “java.security” 文件中的 “securerandom.source=file:/dev/random” 为 “securerandom.source=file:/dev/./urandom “ (参考)[https://stackoverflow.com/questions/36566401/severe-could-not-contact-localhost8005-tomcat-may-not-be-running-error-while]
        1. `cd $JAVA_HOME/jre/lib/security`
        1. 管理员修改权限，`chmod 777 java.security`, 原权限为`chmod 644 java.security`
    1. 也可能，是tomcat内存不够 
        1. 配置tomcat调用的虚拟机内存大小: Linux, 修改`$TOMCAT_HOME/bin/catalina.sh`, 位置`cygwin=false`前。`JAVA_OPTS="-server -Xms256m -Xmx512m -XX:PermSize=64M -XX:MaxPermSize=128m"`（仅做参考，具体数值根据自己的电脑内存配置）

**小程序**

**待(已)解决问题**
1. 管理员扫码登陆，短信验证 _not started_
1. 参加人员也可以在浏览器上扫码进入，如何？能识别视图大小自动调整题目数量吗
1. 微信公众号，发送信息服务错误 _undone_
1. 小程序开发 _in progress_
1. 自动初始化题目数量 _done_
1. 确定logs/sm.log所在远程服务器的位置，通过查看log分析错误 _done_
1. __AJAX发送JSON数据到后台__ _done_
1. 上传Excel文件后，人员信息存入两次 _undone_
    1. DEBUG模式，输出返回的

## AJAX注意点
1. Jquery 完整版不需要slim版本
1. Jackson 引入三个库    
    1. jackson-core
    1. jackson-databind
    1. jackson-annotations
1. `$(document).ready(function(){})`中引入`$.ajax()`, 否则报错
1. `spring-mvc.xml`中加入关于 _json格式数据转换的配置_
1. Ajax向Controller发送String或JSON数据
1. Controller接受值，解析值，处理后返回HASHMAP对象
1. `@ResponseBody`不能省略
1. _contentType : 'application/json; charset=utf-8'_何时使用/省略？_
1.  _Server returned HTTP response code: 400 for URL_
1. Java make http/https request
    1. OutputStrem vs. InputStream
1. preventDefault()作用
1. async:false将关闭异步效果
1. 原理


## 服务器log日志
1. log4j三个基本概念
    1. Logger日志输出器
    1. Appender日志目的地
        1. ConsoleAppender
        1. FileAppender
        1. RollingFileAppender
    1. PatternLayout日志格式
1. 配置文件 `log4j.properties`
1. `web.xml`中添加`org.springframework.web.util.Log4jConfigListener`
1. 服务器log存储位置: _~/apache-tomcat-9.0.8/bin/logs/ssm.log_
1. 遇到的问题
    1. 部署后返回0，原因是，发送的HTTP请求url出现问题，可能是中文字符的转译问题，或者是url中包含了" "空格，需要替换为"" 
    1. tomcat服务器日志记录乱码
    1. log第一行是左对齐，第二行开始后都不是
    1. 服务器日志`conf/logs/`下的`catlina.out`记录了本地测试Console中的全部内容


