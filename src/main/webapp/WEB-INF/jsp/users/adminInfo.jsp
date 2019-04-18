<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2019-04-19
  Time: 00:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>单位管理员信息</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
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

    <% if ((request.getSession().getAttribute("username") != null)) {
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
        <h1 align="center">${adminInfo}</h1>
    </div>
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
<%
    }
%>

</body>
</html>
