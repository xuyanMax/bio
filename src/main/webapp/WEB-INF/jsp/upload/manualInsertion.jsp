<%--
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
<form action="/manualInsertion" method="post" enctype="text/plain" accept-charset="UTF-8"><br/>
    姓名: <input type="text" name="name" required="required"/><br/>
    身份证：<input type="text" name="ID_code" required="required"/><br/>
    单位内编号：<input type="text" name="sn_in_center" /><br/>
    邮箱：<input type="text" name="email" /><br/>
    系统内唯一编号：<input type="text" name="global_sn" required="required"/><br/>
    身份：<input type="radio" name="relative" value="参与人"/>参与人
         <input type="radio" name="relative" value="家属" checked/>家属<br/>
    条形码：<input type="text" name="barcode" /><br/>
    电话1：<input type="text" name="tel1" /><br/>
    电话2：<input type="text" name="tel2" /><br/>
    提交：<input type="submit" name="submit">
    <h1>${message}</h1>
    <h1 color="red">${requestScope.message}</h1>
</form>

<h1>展示部分用户数据</h1>
<c:foreach items="${person}" var="person">
</c:foreach><table>
    <tbody><tr>
        <th>id</th>
        <th>姓名</th>
        <th>年龄</th>
        <th>电话</th>
    </tr>

    <tr>
        <td>${person.idPerson}</td>
        <td>${person.name}</td>
        <td>${person.tel1}</td>
        <td>修改   删除 </td>
    </tr>

    </tbody></table>
</body>
</html>
<%--private Integer idPerson;
private String name;
private String gender;
private int age;
private int ID_code;
private String sn_in_center;
private String global_sn;
private int idcenter;
private String identity;
private String barcode;
private String tel1;
private String tel2;
private String email;
private int relative;--%>
