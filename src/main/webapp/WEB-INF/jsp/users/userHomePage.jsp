<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 13/06/2018
  Time: 02:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>普通用户首页</title>
    <p>欢迎微信用户: ${wxuser.nickname}</p>
    <p>idperson: ${user.idperson}</p>
    <p>${msg}</p>
    <a href="/home">返回主页</a><br/><br/>
    <a href="/informant?idperson1=${user.idperson}">问卷答题</a> <br/>
    <a href="/report">查看报告</a> <br/>
    <a href="/bind/relative">绑定亲属</a><br/>
    <a href="/unbind">微信解绑</a><br/>

</head>
<body>

</body>
</html>
