<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2018/10/16
  Time: 06:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>单位管理员单位内成员列表</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
</head>
<body>
<% if (request.getAttribute("persons") != null && request.getSession().getAttribute("snAdmin") != null) {%>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>成员列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>项目内序号</th>
                    <th>单位内序号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>年龄</th>
                    <th>身份证号后四位</th>
                    <th>编译后的身份证号</th>
                    <th>身份</th>
                    <th>电话</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="person" items="${persons}">
                    <tr>
                        <td>${person.global_sn}</td>
                        <td>${person.sn_in_center}</td>
                        <td>${person.name}</td>
                        <td>${person.gender}</td>
                        <td>${person.age}</td>
                        <td>${person.ID_code_cut}</td>
                        <td>${person.ID_code}</td>
                        <td>${person.identity}</td>
                        <td>${person.tel1}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
<%
    }
%>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>

<!-- popper.min.js 用于弹窗、提示、下拉菜单 -->
<script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>

<!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>
