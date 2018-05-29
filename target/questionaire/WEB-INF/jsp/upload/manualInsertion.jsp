<%@ page import="com.bio.beans.Person" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 24/05/2018
  Time: 00:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manually Insert Personal Data to DB</title>
</head>
<body>
<form action="/admin/manualInsertion" method="post"><br/>
    姓名: <input type="text" name="name" required="required" value="name" placeholder="input name"/><br/>
    身份证：<input type="text" name="ID_code" required="required" value="13010419920518241X"/><br/>
    单位内编号：<input type="text" name="sn_in_center" value="110042"/><br/>
    邮箱：<input type="text" name="email" value="xu.yan11@icloud.coms"/><br/>
    系统内唯一编号：<input type="text" name="global_sn" required="required" value="140662"/><br/>
    身份：<input type="radio" name="relative" value=1 checked/>参与人
         <input type="radio" name="relative" value=0 />家属<br/>
    条形码：<input type="text" name="barcode" value="security*&^"/><br/>
    电话1：<input type="text" name="tel1" value="15151528348"/><br/>
    电话2：<input type="text" name="tel2" value="110"/><br/>
    提交：<input type="submit" name="submit">
    <h1 color="red">${requestScope.message}</h1>
</form>
<%
    List<Person> persons = (List<Person>) request.getAttribute("persons");
    if (persons != null) {
%>
    <table border="1" width="100%">
        <thead>注册用户数据展示</thead>
        <tr>
            <td>id</td>
            <td>name</td>
            <td>gender</td>
            <td>邮箱</td>
            <td>单位内编号</td>
            <td>系统内唯一编号</td>
            <td>身份</td>
            <td>条形码</td>
            <td>tel1</td>
            <td>tel2</td>
            <td>数据库操作</td>
        </tr>
        <%for (Person p:persons){%>
        <tr>
            <td><%= p.getIdperson()%></td>
            <td><%= p.getName()%></td>
            <td><%= p.getGender()%></td>
            <td><%= p.getEmail()%></td>
            <td><%= p.getSn_in_center()%></td>
            <td><%= p.getGlobal_sn()%></td>
            <td><%= p.getRelative()%></td>
            <td><%= p.getBarcode()%></td>
            <td><%= p.getTel1()%></td>
            <td><%= p.getTel2()%></td>
            <td><a href="/admin/updatePerson?idperson=<%=p.getIdperson()%>">更新</a> |
                <a href="/admin/delete?idperson=<%=p.getIdperson()%>">删除</a> </td>
        </tr>
            <%}%><%--persons--%>
    </table>
    <%}%><%--if persons not null--%>
</body>
</html>