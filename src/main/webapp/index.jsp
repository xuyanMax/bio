<%@ page import="com.bio.beans.Person" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Flup</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <%--<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">--%>
    <%--<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>--%>
    <%--<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
</head>
<!------ Include the above in your HEAD tag ---------->
<body>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="navbar-brand" href="/returnHome">FLUP</a>
            </li>
        </ul>
    </div>
    <div class="mx-auto order-0">
        <%--<a class="navbar-brand mx-auto" href="/wx/login">登录</a>--%>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>

    <% if ((request.getSession().getAttribute("username") != null) && (request.getAttribute("snAdmin")) != null) {
    %>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <li class="dropdown"><a href="#" class="dropdown-toggle"
                                    data-toggle="dropdown">Welcome <%=request.getSession().getAttribute("username")%> <b
                    class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="/returnHome">返回首页</a></li>
                    <li class="divider"></li>
                    <li><a href="/logout">退出</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="jumbotron">
        <p align="center" class="glyphicon-font">本系统暂时</p>
        <p align="center">1、只支持chrome/safari浏览器，我们为您提供了32位及64位chrome浏览器的安装包，请根据您的电脑选择安装 <a
                href="https://pan.baidu.com/s/1TloVKXhanqEGdMxQZqUjrw">Chrome32</a>
            , <a href="https://pan.baidu.com/s/1TLYUbhiBPItKpdNAHaP2sQ">Chrome64位</a></p>
        <p align="center">2、您的电脑需要安装java以确保可以下载《队列成员信息表模版》<a href="https://www.java.com/zh_CN/">Java 下载</a></p>
    </div>
</div>
<div class="list-group list">
    <p align="center" style="font-size: 20px; font-family: 'Heiti SC';"><a href="admin/uploadMultiFiles">文件上传</a></p>
    <br/>
    <p align="center" style="font-size: 20px; font-family: 'Heiti SC';"><a
            href="admin/<%=((Person) request.getAttribute("user")).getIdcenter()%>/center/list">查看本单位人员列表</a></p>
    </p>
</div>
<%
} else {

%>
<div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="#">肿瘤易感性筛查</a>
        </li>
    </ul>
</div>
</nav>
<div class="container">
    <div class="jumbotron">
        <h1 align="center">基于职业人群队列的中国常见遗传性肿瘤大规模筛查</h1></br></br>
        <p style="text-indent:2em">肿瘤本质上是一种基因缺陷疾病，肿瘤细胞的相关基因缺陷可能是从父母继承，也可能是个体在出生后独立发生的突变，因此最终的发病取决于导致基因缺陷的遗传因素和环境因素的综合效应。针对高危人群进行早期预防和干预，仍是目前肿瘤防控最有效的方法。前瞻性队列研究临床可操作性高，国际上最早的大样本队列研究可以追溯至20世纪40年代，以美国弗雷明汉心脏研究、英国国家儿童出生队列、美国护士健康队列和欧洲癌症与营养前瞻性队列等为代表的队列研究项目逐渐形成规模，为阐明病因并发展相应的疾病预防措施提供了证据。
        </p> </br>
        <p style="text-indent:2em">响应“关口前移、预防为主”的国家总体导向，基于大数据，对遗传因素和生活方式等外部因素进行相对定量分析，对肿瘤精准防控有重要意义。中国疾病预防控制中心慢性非传染性疾病预防控制中心与国家人类基因组南方研究中心（暨上海人类基因组研究中心）共同发起了全国职业人群肿瘤风险普查研究，依托在线问卷调查信息系统，对高危人群，进行遗传性肿瘤相关基因序列捕获和测序分析，改进肿瘤风险评估方法，形成一套集肿瘤患病风险咨询和预警、生活习惯指导以及在线家庭医生服务于一体的职业人群健康管理体系。</p>
        </br>
        </br>
        <div class="">
            <p align="center"><a class="btn btn-primary btn-lg" role="button" href="/wx/login">
                点击登录网页版系统</a> &nbsp;&nbsp;
                <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">点击登录微信版系统</button>
            </p>
        </div>

        <%--<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">开始演示模态框</button>--%>
        <!-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <%--<div class="modal-header">--%>
                        <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
                        <%--<h4 class="modal-title" id="myModalLabel">--%>
                            <%--点击登录微信版系统--%>
                        <%--</h4>--%>
                    <%--</div>--%>
                    <div class="modal-body"><img src="/images/扫码_搜索联合传播样式-标准色版.jpeg"
                        class="mx-auto d-block" alt="Center Image" width="100%" height="auto"></div>
                    <div class="modal-body">请扫码进入公众号，点击菜单“科研合作”-“职业人群项目”进入系统。</div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
    </div>
    <%--<div class="container">--%>
        <%--<h2 style="text-align: center;">关注“CHGC南方中心”微信公众号，登录微信版系统</h2>--%>
        <%--<img src="/images/扫码_搜索联合传播样式-标准色版.png"--%>
             <%--class="mx-auto d-block" alt="Center Image" width="100%" height="auto">--%>
    <%--</div>--%>
</div>
<%
    }
%>
<!--测试用-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>
</body>
</html>
