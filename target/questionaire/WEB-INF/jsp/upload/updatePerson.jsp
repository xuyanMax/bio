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
        age: <input type="text" placeholder="${person.age}" value="age"/> <br/>
        gender:<input type="text" placeholder="${person.gender}" value="gender"/> <br/>
        email:<input type="text" placeholder="${person.email}" value="email"/><br/>
        sn_in_center:<input type="text" placeholder="${person.sn_in_center}" value="sn_in_center"/><br/>
        global_sn:<input type="text" placeholder="${person.global_sn}" value="global_sn"/><br/>
        tel1:<input type="text" placeholder="${person.tel1}" value="tel1"/><br/>
        barcode:<input type="text" placeholder="${person.barcode}" value="barcode"/><br/>
        relative:<input type="text" placeholder="${person.relative}" value="relative"/><br/>
        submit: <input type="submit" value="update">
        <%
            }
        %>

</body>
</html>
