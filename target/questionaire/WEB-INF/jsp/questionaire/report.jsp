<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2019-05-16
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="UTF-8">
    <title>问卷答题结果</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
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

    <% if ((request.getSession().getAttribute("username") != null)
            && (request.getAttribute("snAdmin")) != null) {
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

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <br/>
            <br/>
            <h3>
                您的问卷已做完， 感谢参与！
            </h3>
            <p>
                系统对您答题一致性评分为 ${count}。 如果您的分数低于60分，系统计算的患癌风险值可能无法反映真实情况，建议您退回主界面重新答题。
            </p>
            <p>
                您的近期（五年）综合患肿瘤风险值为${fyrs_score}，您的综合患肿瘤风险值是${lifetime_score}。

            <% if (request.getAttribute("modelnames") != null) {%>
            根据您的问卷答案无法计算出 ${modelnames} 癌的风险值，综合风险值中不包含 ${modelnames} 癌的风险值。 您可以返回主页面重新答题。如若仍无法解决请联系管理员询问具体情况"
            <% } %>
            此风险值为${evaluation}。
            <p>
                如果您需要参与免费的相关基因检测，进一步完善风险预测信息，请联系单位管理员。
            </p>

        </div>
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
<!--测试用-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>
</body>
</html>