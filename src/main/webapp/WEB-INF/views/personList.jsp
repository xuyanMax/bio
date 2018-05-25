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
    <div>
        <table border="1">
            <tr>
                <th>name</th>
                <th>age</th>
                <th>gender</th>
                <th>tel</th>
            </tr>
            <c:forEach items="${requestScope.persons}" var="person">
                <tr>
                    <td>${person.name}</td>
                    <td>${person.age}</td>
                    <td>${person.gender}</td>
                    <td>${person.tel1}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>
