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
        <table border="1" width="100%">
            <thead>注册用户数据展示</thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>age</th>
                <th>gender</th>
                <th>sn_in_center</th>
                <th>global_sn</th>
                <th>relative</th>
                <th>email</th>
                <th>tel1</th>
                <th>tel2</th>
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
                </tr>
            </c:forEach>
        </table>

</body>
</html>
