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
    <meta name="_token" content="{{ csrf_token() }}">
    <title>注册页面</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous">
    </script>
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
    <form name="dataInputForm" action="" method="post" onsubmit="return checkVCode()">
        <h2 class="text-center">用户注册</h2>
        <div class="form-group" id="name_div">
            <input type="text" class="form-control" placeholder="姓名" required="required" name="name" id="name">
            <small class="help-block" id="name-error"></small>
        </div>
        <div class="form-group" id="ID_CODE_div">
            <input type="text" class="form-control" onchange="checkID()" placeholder="身份证号" required="required" name="id_code" id="id_code">
            <small class="help-block" id="id-error"></small>
        </div>
        <div class="form-group" id="unit_div">
            <label class="">
                <input type="radio" class="form-control" required id="unit1" name="unit_select" id="unit1" value="0">单位1
            </label>
            <label class="">
                <input type="radio" class="form-control" required id="unit2" name="unit_select" id="unit2" value="1">单位2
            </label>
            <label class="">
                <input type="radio" class="form-control" required id="unit3" name="unit_select" id="unit3" value="2">单位3
            </label>
        </div>
        <div class="form-group" id="tel_div">
            <input type="text" onchange="checkOnSignUp()" class="form-control" placeholder="手机号码" name="phone" id="phone" required>
            <small class="help-block" id="tel-error"></small>

            <input type="text" id="vcode" class="form-control" placeholder="输入手机验证码" required>
            <input type="button" class="button btn-sm" id="btn" value="点击获取验证码" disabled=""/>
            <small class="help-block" id="vcode-error"></small>
        </div>
        <div class="form-group">
            <button type="submit" id="submit" class="btn btn-primary btn-block">提交注册</button>
        </div>
    </form>

</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

<script type="text/javascript">
    var count=60;////间隔函数，1秒执行
    var InterValObj;//timer变量，控制时间
    var vcode = ""; //验证码
    var codeLength = 6;//验证码长度
    var currCount = 0;//当前剩余秒数
    var btn = $("#btn");

    function checkOnSignUp() {
        //clear
        document.getElementById('tel-error').innerText = "";
        var phone = document.getElementById("phone").value;
        var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (reg.test(phone)){
            btn.removeAttr("disabled");
            return;
        }else{
            document.getElementById("phone").className +=' is-invalid';
            document.getElementById("tel-error").className += ' text-danger';
            document.getElementById('tel-error').innerText = "请输入合法手机号";
            btn.attr("disabled", true);
            return;
        }
    }
    function checkID() {
        var id_code = document.getElementById("id_code");
        var id_code_err = document.getElementById("id-error");
        id_code.innerText="";
        var reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        if (!reg.test(id_code.value)){
            id_code.className += ' is-invalid';
            id_code_err.className +=' text-danger';
            id_code_err.innerText="请输入18位合法身份证";
            return;
        }
        return;
    }
    function checkVCode() {
        return $("#vcode").val() == vcode?true:false;
    }

    $(document).ready(function () {
        var user = '';
        $("#btn").on("click", function () {
            currCount = count;
            // 设置button效果，开始计时
            btn.attr("disabled", true);
            btn.val("请在" + currCount + "秒内输入验证码");

            InterValObj = window.setInterval(setRemainTime, 1000);

            //产生验证码
            for ( var i=0; i<codeLength; i++)
                vcode += parseInt(Math.random()*9).toString();

            var upload={};
            upload.vcode = vcode;
            upload.phone = $("#phone").val();
            upload.idcode = $("#id_code").val();
            $.ajax({
                type: "POST", //用POST方式传输
                dataType: "json", //数据格式:JSON
                url: "register/sms", //目标地址
                data: upload, //post携带数据
                error: function () {alert("错误提交"); }, //请求错误时的处理函数
                success: function (data){
                    if (data.result == '1'){
                        document.getElementById("vcode-error").innerText="短信验证码已发送，请查收";
                        alert("成功发送短信到手机");
                        //获取不到session，如果不刷新页面
                        <%--var session_wxuser = '${sessionScope.get("wxuser")}';--%>
                        // wxuser = '\''+data.wxuser+'\'';
                        wxuser = data.wxuser;
                        // alert(wxuser);
                        user = $.parseJSON(wxuser);
                        // alert(user.openid+"return from sms");
                    }else if (data.result == '0'){
                        document.getElementById("vcode-error").className=' text-danger';
                        document.getElementById("vcode-error").innerText = "短信验证码发送失败，请重新发送";
                        alert("失败");
                    }else if (data.result == '-1'){
                        document.getElementById("id_code").className=' is-valid';
                        document.getElementById("id-error").className=' text-danger';
                        document.getElementById("id-error").innerText="没有您的预申请信息，请联系专属管理员。";
                    }
                } //请求成功时执行的函数
            });
        });
        function setRemainTime(){
            if (currCount == 0){
                window.clearInterval(InterValObj);// 停止计时器
                $("#btn").removeAttr("disabled");// 启用按钮
                $("#btn").val("重新发送验证码");
                vcode = ""; // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
            }else{
                currCount--;
                $("#btn").val("请在" + currCount + "秒内输入验证码");
            }
        }
        /*不带form的action*/

        $("#submit").on("click", function () {
            // 向后台发送处理数据
            var upload = {};
            upload.vcode = vcode;
            upload.phone = $("#phone").val();
            upload.id = $("#id_code").val();
            upload.name = $("#name").val();
            alert(upload.vcode);
            if ( user != "" ) {
                upload.opd = user.openid;
                upload.uid = user.unionid;
                upload.subs = user.subscribe;
                upload.sub_time = user.subscribe_time;
                upload.city = user.city;
                upload.nickname = user.nickname;
                upload.headImgUrl = user.headImgUrl;
                upload.province = user.province;
                upload.sex = user.sex;
                upload.language = user.language;
                upload.idperson = user.idperson;
                // alert(user.sex);

            }
            alert(upload);

            $.ajax({
                type: "POST",
                dataType: "json",
                url: "register/checkVcode",
                data: upload,
                error: function () {
                    alert("输入验证码错误！");
                },
                success: function (data) {
                    if (data.result == 1) {
                        vcode = data.result;
                        alert("验证成功!");
                        window.location.assign(window.location.origin+"/login");
                    } else if (data.result == 0){
                        alert("验证失败");
                    }
                }
            });
        });
    });

</script>
</body>
</html>
