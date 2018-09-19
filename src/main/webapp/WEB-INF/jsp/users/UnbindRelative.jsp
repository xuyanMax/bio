<%@ page import="com.bio.beans.Person" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2018/9/15
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>

<title>解绑亲属界面</title>
<%
    List<Person> persons = (List<Person>) request.getAttribute("relatives");
    if (persons != null){
%>
<table border="1" width="80%">
    <thead>亲属</thead>
    <tr>
        <th>亲属姓名</th>
        <th>开始问卷</th>
    </tr>
</table>
<c:forEach items="${requestScope.persons}" var="person">
    <tr>
        <th>${person.idperson}</th>
        <th><a href="/informant"></a></th>
    </tr>
</c:forEach>
<%
    }
%>


</body>
</html>
