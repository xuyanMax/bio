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
    <form name="dataInputForm" action="/register" method="post">
        <h2 class="text-center">用户注册</h2>
        <div class="form-group" id="name_div">
            <input type="text" class="form-control" placeholder="姓名" required="required" name="name">
            <small class="help-block" id="name-error"></small>
        </div>
        <div class="form-group" id="tel_div">
            <p>手机号码</p>
            <input type="text" onchange="checkOnSignUp()" class="form-control" placeholder="手机号码" name="phone" id="phone" required>
            <small class="help-block" id="tel-error"></small>
            <input type="text" id="vcode" class="form-control" value="输入手机验证码" required>
            <input type="button" class="button btn-sm" id="btn" value="点击获取验证码" onclick="sendMsg()" disabled="disabled"/>
            <span id="phoneTip"></span>
            <small class="help-block" id="vcode-error"></small>
        </div>
        <div class="form-group">
            <button type="submit" id="submit" class="btn btn-primary btn-block">提交注册</button>
        </div>
    </form>

</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

<script type="text/javascript">
    var count=60;////间隔函数，1秒执行
    var InterValObj;//timer变量，控制时间
    var vcode = ""; //验证码
    var codeLength = 6;//验证码长度
    var curCount;//当前剩余秒数

    function checkOnSignUp() {
        var phone = document.getElementById("phone");
        var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (reg.test(phone)){
            btn.removeAttr("disabled");
            return;
        }else{
            document.getElementById("phone").className +='is-invalid';
            document.getElementById("tel-error").className += 'text-danger';
            document.getElementById('tel-error').innerText = "请输入合法手机号";
            btn.attr("disabled", true);
            return;
        }
    }
    function sendMsg(){
        // 设置button效果，开始计时
        btn.attr("disabled", true);
        $("#btn").val("请在" + curCount + "秒内输入验证码");

        InterValObj = window.setInterval(setRemainTime, 1000);

        //产生验证码
        for ( var i=0; i<codeLength; i++)
            vcode += parseInt(Math.random()*9).toString();

        // 向后台发送处理数据
        $.ajax({
            type: "POST", //用POST方式传输
            dataType: "json", //数据格式:JSON
            url: "/register/sms/", //目标地址
            data: "vcode="+vcode, //post携带数据
            error: function () { }, //请求错误时的处理函数
            success: function (data){
                if (data == 1){
                    $("#vcode-error").val("短信验证码已发送，请插手");
                    alert("成功");
                }else if (data == 0){
                    $("#vcode-error").className += 'text-danger';
                    $("#vcode-error").val("短信验证码发送失败，请重新发送");
                    alert("失败");
                }
            } //请求成功时执行的函数
        });

    }
    function setRemainTime(){
        if (curCount == 0){
            window.clearInterval(InterValObj);// 停止计时器
            $("#vcode").removeAttr("disabled");// 启用按钮
            $("#vcode").val("重新发送验证码");
            vcode = ""; // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
        }else{
            curCount--;
            $("#vcode").val("请在" + curCount + "秒内输入验证码");
        }
    }
    /*不带form的action*/
    $("#submit").on("submit", function (ev) {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/register/checkVcode",
            data: "",
            error: function () {
            },
            success: function (data) {
                if (data == 1) {

                } else if (data == 0){

                }
            }
        });
        ev.preventDefault();

    });
</script>
</body>
</html>
