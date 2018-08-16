<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 01/06/2018
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
reference:
https://www.tutorialrepublic.com/codelab.php?topic=bootstrap&file=simple-login-form
--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
<div class="login-form">
    <form name="signInForm" action="/Login" method="post" onsubmit="return checkOnSignIn();">
        <h2 class="text-center">Log in</h2>
        <div class="form-group" id="name_div">
            <input type="text" class="form-control" placeholder="姓名" required="required" name="name">
            <span class="help-block" id="name-error"></span>
        </div>
        <div class="form-group" id="id1_div">
            <input type="text" class="form-control" placeholder="身份证号" required="required" name="ID_code" id="id1">
            <span class="help-block" id="ID-error1"></span>
        </div>
        <div class="form-group" id="id2_div">
            <input type="text" class="form-control" placeholder="身份证号" required="required" name="ID_code_" id="id2">
            <span class="help-block" id="ID-error2"></span>
        </div>
        <%--登陆错误提示--%>
        <% if (request.getAttribute("error") != null){
        %>
        <div class="form-group has-error">
            <span class="form-control">${error}</span>
        </div>
        <%
            }
        %>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">登陆</button>
        </div>
        <div class="form-group">
            <a href="/signupPage" class="btn btn-primary btn-block">注册</a>
        </div>
        <%--<div class="clearfix">
            <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label>
            <a href="#" class="pull-right">Forgot Password?</a>
        </div>--%>
    </form>
    <%--<p class="text-center"><a href="#">Create an Account</a></p>--%>

</div>
<script type="text/javascript">
    function clear() {
        document.getElementById("ID-error2").innerText="";
        document.getElementById("ID-error1").innerText="";
        document.getElementById("name-error").innerText="";
    }
    function checkOnSignIn() {
        clear();
        var id1 = document.getElementById("id1").value;
        var id2 = document.getElementById("id2").value;

        // alert(id1);
        // alert(id2);
        // todo: 限制为18
        if (id1.length!=18){
            var id_class = document.getElementById("id1_div");
            id_class.className += ' has-error';
            document.getElementById("ID-error1").innerText="身份证号必须为18位";
            // alert(document.getElementById("id1_div").className);
            return false;
        }
        if ( id2.length!=18){
            var id_class = document.getElementById("id2_div");
            id_class.className += ' has-error';
            document.getElementById("ID-error2").innerText="身份证号必须为18位";
            return false;
        }
        if (id1 != id2){
            var id_class = document.getElementById("id2_div");
            id_class.className += ' has-error';
            document.getElementById("ID-error2").innerText="两次输入身份证号不匹配";
            return false;
        }
        return true;
    }
</script>
</body>
</html>