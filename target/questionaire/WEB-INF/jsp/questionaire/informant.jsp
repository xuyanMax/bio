<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2018/9/14
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>知情同意书</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="text-align:center">
<form class="form-inline" action="/survey" method="post" style="margin-left: auto; margin-right: auto">
    <div class="checkbox">
        <label>
            <input type="checkbox" id="agree2" name="agree" value="1" onclick="agreed()">我认真阅读并接受以上协议</input>
        </label>
    </div>
    <input type="hidden" id="gender2" name="gender" value="${gender}"/>
    <button class="btn btn-default" type="submit" name="submit2" disabled="true" id="submit2">同意，开始作答</button>
    <a href="/userHomePage" class="button">不同意</a>
</form>
<div class="container">
    <h2 align="center">知情同意书</h2>
    <img src="/images/inform.jpg" class="img-responsive" alt="Cinque Terre" align="center">
</div>

<form class="form-inline" action="/survey" method="post" style="margin-left: auto; margin-right: auto">
    <div class="checkbox">
        <label>
            <input type="checkbox" id="agree" name="agree" value="1" onclick="agreed()">我认真阅读并接受以上协议</input>
        </label>
    </div>
    <input type="hidden" id="gender" name="gender" value="${gender}"/>
    <button class="btn btn-default" type="submit" name="submit" disabled="true" id="submit">同意，开始作答</button>
    <a href="/userHomePage" class="button">不同意</a>
</form>

<script type="text/javascript">
    function agreed() {
        if (document.getElementById("agree").checked == true) {
            document.getElementById("submit").disabled = false;
            return true;
        } else if (document.getElementById("agree2").checked == true) {
            document.getElementById("submit2").disabled = false;
            return true;
        }
        return false
    }
</script>

</body>

</html>
