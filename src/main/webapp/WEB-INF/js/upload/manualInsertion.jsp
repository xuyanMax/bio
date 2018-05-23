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
<form action="/manualInsertion" method="post" enctype="text/plain" accept-charset="UTF-8">
    姓名: <input type="text" name="name"/>
    身份证：<input type="text" name="ID_code"/>
    单位内编号：<input type="text" name="sn_in_center"/>
    邮箱：<input type="text" name="email"/>
    亲属：<input type="text" name="ID_code"/>
    身份证：<input type="text" name="ID_code"/>
    系统内唯一编号：<input type="text" name="global_sn"/>
    条形码：<input type="text" name="barcode"/>
    电话1：<input type="text" name="tel1"/>
    电话2：<input type="text" name="tel2"/>
</form>

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
