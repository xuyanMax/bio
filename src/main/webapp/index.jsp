<%@ page import="com.bio.beans.Person" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title>后台控制</title>
 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
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

    <% if ( (request.getSession().getAttribute("username") != null) && (request.getAttribute("snAdmin")) != null) {
    %>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome <%=request.getSession().getAttribute("username")%> <b class="caret"></b></a>
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
        <p align="center">1、只支持chrome/safari浏览器，我们为您提供了32位及64位chrome浏览器的安装包，请根据您的电脑选择安装 <a href="https://pan.baidu.com/s/1TloVKXhanqEGdMxQZqUjrw">Chrome32</a>
            , <a href="https://pan.baidu.com/s/1TLYUbhiBPItKpdNAHaP2sQ">Chrome64位</a></p>
        <p align="center">2、您的电脑需要安装java以确保可以下载《队列成员信息表模版》<a href="https://www.java.com/zh_CN/">Java 下载</a></p>
    </div>
</div>
<div class="list-group list">
    <p align="center" style="font-size: 20px; font-family: 'Heiti SC';"><a href="admin/uploadMultiFiles">文件上传</a></p><br/>
    <%--<p align="center" style="font-size: 20px;"><a href="admin/manualInsertPage">手动输入用户信息</a></p><br/>--%>
    <p align="center" style="font-size: 20px; font-family: 'Heiti SC';"><a href="admin/<%=((Person) request.getAttribute("user")).getIdcenter()%>/center/list">查看本单位人员列表</a></p>
    <p><%=((Person) request.getAttribute("user")).getIdcenter()%></p>
</div>
<%
    }else{

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
        <h1 align="center">Flup</h1>
        <h1 align="center">职业人群健康体检筛查项目</h1></br></br>
    </div>
</div>
<%
    }
%>
<!--测试用-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>
</html>
