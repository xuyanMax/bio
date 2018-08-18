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
    <title>多文件上传</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<%--todo: 添加jQuery文件上传插件; 解决Bootstrap路径识别问题--%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="navbar-brand" href="/home">FLUP</a>
            </li>
            <%--<li class="nav-item">--%>
            <%--<a class="nav-link" href="#">Link</a>--%>
            <%--</li>--%>
            <%--<li class="nav-item">--%>
            <%--<a class="nav-link" href="#">Link</a>--%>
            <%--</li>--%>
            <%--<li class="nav-item">--%>
            <%--<a class="nav-link" href="#">Link</a>--%>
            <%--</li>--%>
        </ul>
    </div>
    <div class="mx-auto order-0">
        <a class="navbar-brand mx-auto" href="/download">下载表格模版</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <% if (request.getAttribute("username") != null) {

        %>
        <ul class="navbar-nav ml-auto">
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome, ${username} <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="/preferences"><i class="icon-cog"></i> Preferences</a></li>
                    <li><a href="/help/support"><i class="icon-envelope"></i> Contact Support</a></li>
                    <li class="divider"></li>
                    <li><a href="/logout"><i class="icon-off"></i> Logout</a></li>
                </ul>
            </li>
        </ul>
        <%
            }
        %>
    </div>
</nav>

<form class="" action="/admin/upMultiFiles" enctype="multipart/form-data" method="post">
    <!-- 可以选取一张或者多种图片上传 -->
    请上传2003版Excel文件: <input type="file" id="files" name="files" multiple="multiple"
                                      required="required" accept=".xls" onchange="getFiles();"><br/>
    上传: <input type="submit" value="submit">
</form>
上传文件:<br/>
<div class="list-group" id="fileNames"></div>



<!--测试用-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<script type="text/javascript">
    function getFiles() {
        var files = document.getElementById("files");
        var span = document.getElementById("fileNames");
        for (i=0; i<files.files.length; i++){
            // alert(files.files[i].name);
            console.log(files.files[i].name);
            span.innerHTML += "<span class=\"list-group\" id=\"fileNames\">"+files.files[i].name+"</span>";
        }
    }
</script>
</body>
</html>
