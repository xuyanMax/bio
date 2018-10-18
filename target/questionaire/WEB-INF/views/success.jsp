<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 23/05/2018
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
    <%--<p>upload success...</p>
    <a href="">Click to download the uploaded file</a> <br/>
    <a href="/list">View uploaded files</a>--%>
</head>
<body>
<a href="/home">返回主页</a>
<br/>
<p>上传成功
</p>
<a href="/admin/uploaded/download?fileName=<%=request.getAttribute("fileName")%>">下载文件
</a>
<br/>
请点击下载，《队列成员信息表》将下载到'Downloads'以供查看；若没有下载《队列成员信息表》，请检查您的电脑是否已安装java
<br>
<p style="color:red;text-align:center">${msg}</p>

</body>
</html>
