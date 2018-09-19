<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.bio.beans.Person" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bio.beans.Relative" %><%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2018/9/14
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>亲属绑定</title>
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
    <%
        List<Relative> persons = (List<Relative>) request.getAttribute("persons");
        Person user = (Person) request.getAttribute("user");
        if (persons != null){
    %>
    <a href="/home">返回首页</a>
    <table border="1" width="80%" align="center">
        <%--<thead>亲属</thead>--%>
        <tr>
            <th>亲属姓名</th>
            <th>解除绑定</th>
            <th>开始问卷</th>
        </tr>

    <c:forEach items="${requestScope.persons}" var="person">
        <tr>
            <td>${person.name}</td>
            <td><a href="/deleteRelative?idperson2=${person.idperson}&idperson1=${user.idperson}">解除亲属</a></td>
            <td><a href="/informant?idperson2=${person.idperson}">问卷</a></td>
        </tr>
    </c:forEach>
    </table>
    <%
        }
    %>

    <div class="login-form">
        <form action="/addRelative" method="post" name="signInForm" onsubmit="return checkID()">
            <h2 class="text-center">绑定亲属</h2>
            <div class="form-group">
                <input class="form-control" type="text" id="name" name="name" placeholder="姓名" required>
                <span class="help-block" id="name-error"></span>
            </div>
            <div class="form-group">
                <input class="form-control" type="text" id="ID_code" name="ID_code" onchange="checkID()" placeholder="身份证号" required>
                <span class="help-block" id="id-error"></span>
            </div>
            <div class="form-group">
                <select name="relation" id="relation" class="form-control">
                    <option value="0">父亲/母亲</option>
                    <option value="1">子女</option>
                    <option value="2">兄弟姐妹</option>
                </select>
            </div>
            <div class="form-group">
                <button type="submit" id="submit" name="submit" class="btn btn-primary btn-block">增加亲属</button>
                <span class="help-block text-danger" id="submit-error">${msg}</span>
            </div>
        </form>
    </div>
<script type="text/javascript">
    function checkID() {
        var id_code = document.getElementById("ID_code");
        var id_code_err = document.getElementById("id-error");
        id_code.innerText="";
        var reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        if (!reg.test(id_code.value)){
            id_code.className += ' is-invalid';
            id_code_err.className +=' text-danger';
            id_code_err.innerText="请输入18位合法身份证";
            return false;
        }
        return true;
    }
</script>

</body>
</html>
