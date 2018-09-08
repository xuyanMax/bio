<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2018/8/21
  Time: 08:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_token" content="{{ csrf_token() }}">
    <title>注册页面</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
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
    <form name="dataInputForm" action="" method="post">
        <h2 class="text-center">用户注册</h2>
        <div class="form-group" id="name_div">
            <input type="text" class="form-control" placeholder="姓名" required="required" name="name" id="name">
            <small class="help-block" id="name-error"></small>
        </div>
        <div class="form-group" id="ID_CODE_div">
            <input type="text" class="form-control" onchange="checkID()" placeholder="身份证号" required="required" name="id_code" id="id_code">
            <small class="help-block" id="id-error"></small>
        </div>
        <div class="form-group" id="phone_div">
            <input type="text" class="form-control" onchange="checkPhone()" placeholder="手机号" required="required" name="phone" id="phone">
            <small class="help-block" id="phone-error"></small>
        </div>
        <div class="form-group">
            <button type="submit" id="submit" class="btn btn-primary btn-block">注册查询</button>
        </div>
    </form>

</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<script type="text/javascript">
    function checkPhone(){
        var phone = document.getElementById("phone");
        var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
        phone_err.innerText="";
        phone.innerText="";
        if (!reg.test(phone.value)){
            var phone_err = document.getElementById("phone-error");
            phone.className += ' is-invalid';

            phone_err.className += ' text-danger';
            phone_err.innerText="请输入正确的手机号码!";


            return false;
        }
        return true;
    }
    function checkID() {
        var id_code = document.getElementById("id_code");
        var id_code_err = document.getElementById("id-error");
        id_code_err.innerText="";
        id_code.innerText="";
        var reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        if (!reg.test(id_code.value)){
            id_code.className += ' is-invalid';
            id_code_err.className +=' text-danger';
            id_code_err.innerText="请输入18位合法身份证!";
            return;
        }
        return;
    }
    $(document).ready(function () {
        $("#submit").on("click", function () {
            var idcode = $("#id_code").val();
            var upload={};
            upload.idcode = idcode;
            upload.name = $("#name").val();
            $.ajax({
                type:"POST",
                dataType:"json",
                data:upload,
                url:"register/idcheck",
                error:function () {
                    alert("失败");
                },
                success:function (data) {
                    if (data.result == 0){
                        alert("没有您的预申请信息，请联系专属管理员。")
                    }else if (data.result == 1){
                        alert("身份证号验证成功!");
                        window.location.assign(window.location.origin+"/signupPageFollowed?idcode="+idcode);
                    }
                }
            });
        });
    });
</script>
</body>
</html>
