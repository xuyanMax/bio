<%@ page import="com.bio.beans.Person" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 25/05/2018
  Time: 00:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User info display</title>
</head>
<body>
        <% List<Person> persons = (List<Person>) request.getAttribute("persons");
        if (persons != null){
        %>
        <table border="1" width="100%">
            <thead>展示部分用户</thead>
            <tr>
                <th>id</th>
                <th>姓名</th>
                <th>年龄</th>
                <th>性别</th>
                <th>本地编号(工号)</th>
                <th>系统内编号</th>
                <th>身份</th>
                <th>邮箱</th>
                <th>tel1</th>
                <th>tel2</th>
                <td>数据库操作</td>
            </tr>
            <c:forEach items="${requestScope.persons}" var="person">
                <tr>
                    <td>${person.idperson}</td>
                    <td>${person.name}</td>
                    <td>${person.age}</td>
                    <td>${person.gender}</td>
                    <td>${person.sn_in_center}</td>
                    <td>${person.global_sn}</td>
                    <td>${person.relative}</td>
                    <td>${person.email}</td>s
                    <td>${person.tel1}</td>
                    <td>${person.tel2}</td>
                    <td><a href="/admin/updatePerson?idperson=${person.idperson}">更新</a>|<a href="/admin/delete?idperson=${person.idperson}">删除</a></td>
                </tr>
            </c:forEach>
        </table>
        <%
            } else {
        %>
            <p>暂无数据</p>
        <%
            }
        %>

</body>
</html>