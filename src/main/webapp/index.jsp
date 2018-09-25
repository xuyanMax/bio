<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title>后台控制</title>
</head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<!------ Include the above in your HEAD tag ---------->
<body>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="navbar-brand" href="/home">FLUP</a>
            </li>
            <%--<li class="nav-item">--%>
                <%--<a class="nav-link" href="#">Link</a>--%>
            <%--</li>--%>
            <%--<li class="nav-item">--%>
                <%--<a class="nav-link" href="#">Link</a>--%>
            <%--</li>--%>
            <%--<li class="nav-item">--%>
                <%--<a class="nav-link" href="#">Link</a>--%>
            <%--</li>--%>
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
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome, <%=request.getSession().getAttribute("username")%> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="/preferences"><i class="icon-cog"></i> Preferences</a></li>
                    <li><a href="/help/support"><i class="icon-envelope"></i> Contact Support</a></li>
                    <li class="divider"></li>
                    <li><a href="/logout"><i class="icon-off"></i> Logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="jumbotron">
        <h1 align="center">Flup</h1>
        <h1 align="center">职业人群健康体检筛查项目</h1></br></br>
        <p align="center">本系统暂时</p>
        <p align="center">1、只支持chrome/safari浏览器，我们为您提供了32位及64位chrome浏览器的安装包，请根据您的电脑选择安装</p>
        <p align="center">2、您的电脑需要安装java以确保可以下载《队列成员信息表模版》</p>
    </div>
</div>
<div class="list-group list">
    <a href="admin/uploadMultiFiles" class="list-group-item list-group-item-action active">
    文件上传
    </a> <br/>
    <a href="admin/manualInsertPage" class="list-group-item list-group-item-action">手动输入用户信息</a><br/>
    <a href="https://pan.baidu.com/s/1TloVKXhanqEGdMxQZqUjrw" class="list-group-item list-group-item-action">Chrome 32位下载</a>
    <a href="https://pan.baidu.com/s/1TLYUbhiBPItKpdNAHaP2sQ" class="list-group-item list-group-item-action">Chrome 64位下载</a>
    <a href="https://www.java.com/zh_CN/" class="list-group-item list-group-item-action">Java 下载</a>
    <%--<a href="admin/displayUsers" class="list-group-item list-group-item-action">Display all persons</a><br/>--%>
    <%--<a href="list" class="list-group-item list-group-item-action disabled" disabled="true">List uploaded files</a><br/>--%>
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
        <p align="center"><a class="btn btn-primary btn-lg" role="button" href="/wx/login">
            点击登录</a>
        </p>
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
