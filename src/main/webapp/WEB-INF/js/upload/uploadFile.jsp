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
    <meta http-equiv="Content-Type" content="text/htm" charset="gb2312">
    <title>Uploading data</title>

</head>
<body>
    <h1>Form</h1>
    <form action="/upFile" enctype="multipart/form-data" method="post">
        Please upload Excel file: <input type="file" name="file"><br/>
        Please submit: <input type="submit" value="submit">
    </form>

</body>
</html>
