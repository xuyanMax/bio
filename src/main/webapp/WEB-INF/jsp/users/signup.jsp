<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2018/7/28
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>注册页面</title>
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
    <form name="dataInputForm" action="/admin/manualInsertion" method="post">
        <h2 class="text-center">用户注册</h2>
        <div class="form-group" id="name_div">
            <input type="text" class="form-control" placeholder="姓名" required="required" name="name">  d
            <small class="help-block" id="name-error"></small>
        </div>
        <div class="form-group" id="ID_code_div">
            <input type="text" onchange="checkID();" class="form-control" placeholder="身份证号" required="required" name="ID_code" id="ID_code">
            <small class="help-block" id="ID-error"></small>
        </div>
        <div class="form-group" id="sn_div">
            <input type="text" class="form-control" placeholder="单位内编号(工号)" required="required" name="sn_in_center" id="sn_in_center">
            <small class="help-block" id="sn-error"></small>
        </div>
        <div class="form-group" id="tel1_div">
            <input type="text" class="form-control" placeholder="电话1" name="tel1" id="tel1">
            <small class="help-block" id="tel1-error"></small>
        </div>
        <div class="form-group" id="tel2_div">
            <input type="text" class="form-control" placeholder="电话2" name="tel2" id="tel2">
            <small class="help-block" id="tel2-error"></small>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">提交注册</button>
        </div>
        <%--<div class="clearfix">
            <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label>
            <a href="#" class="pull-right">Forgot Password?</a>
        </div>--%>
    </form>

</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>
</html>
