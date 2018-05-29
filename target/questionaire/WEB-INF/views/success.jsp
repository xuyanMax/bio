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
        某一项操作成功!显示数字代表数据插入成功/获取数据库List<Person>成功；<br/>
        ${person.idperson}<%--手动插入/上传excel/删除 都会返回idperson--%>
        ${persons.size()}<%--displayAllUsers--%>
        <h1>${files}</h1><%--list all uploaded files--%>
            <a href="/home">返回主页</a>
</body>
</html>
