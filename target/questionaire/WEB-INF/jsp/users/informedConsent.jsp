<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2019-04-27
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>知情同意书</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    ${style}
</head>

<body lang=ZH-CN link="#000000" vlink=fuchsia>
${body}
<form class="form-inline" action="/survey" method="post" style="margin-left: auto; margin-right: auto">
    <p style="font-size: 20pt; color: red;" align="center">如果您继续注册，视同同意上述事项。</p>
    <input type="hidden" id="gender" name="gender" value="${gender}"/>
    <button class="btn btn-default" type="submit" name="submit" disabled="true" id="submit">下一步</button>
    <a href="/userHomePage" class="button">不同意</a>
</form>

</body>

</html>
