<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 23/05/2018
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>--%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>数据上传</title>

</head>
<body>
    <h1>Form</h1>
    <form action="/upload" enctype="multipart/form-data" method="post">
        请上传Excel文件: <input type="file" name="file">
        请提交: <input type="submit" value="submit">
    </form>

</body>
</html>
