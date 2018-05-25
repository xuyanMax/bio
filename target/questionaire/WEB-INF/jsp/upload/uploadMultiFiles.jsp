<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 23/05/2018
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload multiple files</title>
</head>
<body>

<h1>Form</h1>
<form action="/upMultiFiles" enctype="multipart/form-data" method="post">
    <!-- 可以选取一张或者多种图片上传 -->
    Please upload Excel files: <input type="file" name="files" multiple="multiple" required="required"><br/>
    Please submit: <input type="submit" value="submit">
</form>


</body>
</html>
