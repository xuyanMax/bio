<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 29/05/2018
  Time: 01:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Personal data</title>
</head>
<body>
        <% if (request.getAttribute("person") != null){
        %>
        <form method="post" action="/admin/update"></form>
        age: <input type="text" value="${person.age}"/> <br/>
        gender:<input type="text" value="${person.gender}"/> <br/>
        email:<input type="text" value="${person.email}"/><br/>
        sn_in_center:<input type="text" value="${person.sn_in_center}"/><br/>
        global_sn:<input type="text" value="person.global_sn"/><br/>
        tel1:<input type="text" value="${person.tel1}"/><br/>
        barcode:<input type="text" value="${person.barcode}"/><br/>
        relative:<input type="text" value="${person.relative}"/><br/>
        submit: <input type="submit" value="update">
        <%
            }
        %>

</body>
</html>
