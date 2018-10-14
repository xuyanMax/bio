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
    <title>输入用户数据</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <style type="text/css">
        .login-form {
            width: 340px;
            margin: 50px auto;
        }
        .login-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        .login-form h2 {
            margin: 0 0 15px;
        }
        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }
        .btn {
            font-size: 15px;
            font-weight: bold;
        }
    </style>

</head>
<body>
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
        <%--<a class="navbar-brand mx-auto" href="#">Navbar</a>--%>
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
                    <li><a href="/returnHome">返回首页</a></li>
                    <li><a href="/logout">退出</a></li>
                </ul>
            </li>
        </ul>
        <%
            }
        %>
    </div>
</nav>


<div class="login-form">
    <form name="dataInputForm" action="/admin/manualInsertion" method="post">
        <h2 class="text-center">填写用户数据</h2>
        <div class="form-group" id="name_div">
            <input type="text" class="form-control" placeholder="姓名" required="required" name="name">
            <small class="help-block" id="name-error"></small>
        </div>
        <div class="form-group" id="ID_code_div">
            <input type="text" onchange="checkID();" class="form-control" placeholder="身份证号" required="required" name="ID_code" id="ID_code">
            <small class="help-block" id="ID-error"></small>
        </div>
        <div class="form-group" id="email_div">
            <input type="text" class="form-control" placeholder="邮箱" name="email" id="email">
            <small class="help-block" id="email-error"></small>
        </div>
        <div class="form-group" id="sn_div">
            <input type="text" class="form-control" placeholder="单位内编号(工号)" required="required" name="sn_in_center" id="sn_in_center">
            <small class="help-block" id="sn-error"></small>
        </div>
        <div class="form-group" id="bar_div">
            <input type="text" class="form-control" placeholder="条形码" required="required" name="barcode" id="barcode">
            <small class="help-block" id="bar-error"></small>
        </div>
        <div class="form-control" id="relative">
            <label class="checkbox inline">
                <input type="checkbox" id="inlineCheckbox1" name="relative" value="0"> 职员
            </label>
            <label class="checkbox inline">
                <input type="checkbox" id="inlineCheckbox2" name="relative" value="1"> 家属
            </label>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">上传数据</button>
        </div>
        <%--<div class="clearfix">
            <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label>
            <a href="#" class="pull-right">Forgot Password?</a>
        </div>--%>
    </form>

</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<script type="text/javascript">
    function checkID() {
        document.getElementById("ID-error").innerText="";
        var reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        var id = document.getElementById("ID_code").value;
        if (!reg.test(id)){
            document.getElementById("ID_code").className += ' is-invalid';
            document.getElementById("ID-error").className +=' text-danger';
            document.getElementById("ID-error").innerText="请输入18位合法身份证";
            return;
        }
    }
    function clear() {
        document.getElementById("name-error").innerText="";
        document.getElementById("ID-error").innerText="";
        document.getElementById("email-error").innerText="";
        document.getElementById("sn-error").innerText="";
        document.getElementById("relative-error").innerText="";
        document.getElementById("tel1-error").innerText="";
        document.getElementById("tel2-error").innerText="";
    }
</script>
</body>
</html>