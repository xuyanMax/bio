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
    <title>Manually Insert Personal Data to DB</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <!-- Website Font style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
    <style type="text/css">
/* Created by Filipe Pina
 * Specific styles of signin, register, component

 * General styles
 */
        #playground-container {
            height: 500px;
            overflow: hidden !important;
            -webkit-overflow-scrolling: touch;
        }
        body, html{
            height: 100%;
            background-repeat: no-repeat;
            background:url(https://i.ytimg.com/vi/4kfXjatgeEU/maxresdefault.jpg);
            font-family: 'Oxygen', sans-serif;
            background-size: cover;
        }

        .main{
            margin:50px 15px;
        }

        h1.title {
            font-size: 50px;
            font-family: 'Passion One', cursive;
            font-weight: 400;
        }

        hr{
            width: 10%;
            color: #fff;
        }

        .form-group{
            margin-bottom: 15px;
        }

        label{
            margin-bottom: 15px;
        }

        input,
        input::-webkit-input-placeholder {
            font-size: 11px;
            padding-top: 3px;
        }

        .main-login{
            background-color: #fff;
            /* shadows and rounded borders */
            -moz-border-radius: 2px;
            -webkit-border-radius: 2px;
            border-radius: 2px;
            -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);

        }
        .form-control {
            height: auto!important;
            padding: 8px 12px !important;
        }
        .input-group {
            -webkit-box-shadow: 0px 2px 5px 0px rgba(0,0,0,0.21)!important;
            -moz-box-shadow: 0px 2px 5px 0px rgba(0,0,0,0.21)!important;
            box-shadow: 0px 2px 5px 0px rgba(0,0,0,0.21)!important;
        }
        #button {
            border: 1px solid #ccc;
            margin-top: 28px;
            padding: 6px 12px;
            color: #666;
            text-shadow: 0 1px #fff;
            cursor: pointer;
            -moz-border-radius: 3px 3px;
            -webkit-border-radius: 3px 3px;
            border-radius: 3px 3px;
            -moz-box-shadow: 0 1px #fff inset, 0 1px #ddd;
            -webkit-box-shadow: 0 1px #fff inset, 0 1px #ddd;
            box-shadow: 0 1px #fff inset, 0 1px #ddd;
            background: #f5f5f5;
            background: -moz-linear-gradient(top, #f5f5f5 0%, #eeeeee 100%);
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #f5f5f5), color-stop(100%, #eeeeee));
            background: -webkit-linear-gradient(top, #f5f5f5 0%, #eeeeee 100%);
            background: -o-linear-gradient(top, #f5f5f5 0%, #eeeeee 100%);
            background: -ms-linear-gradient(top, #f5f5f5 0%, #eeeeee 100%);
            background: linear-gradient(top, #f5f5f5 0%, #eeeeee 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f5f5f5', endColorstr='#eeeeee', GradientType=0);
        }
        .main-center{
            margin-top: 30px;
            margin: 0 auto;
            max-width: 400px;
            padding: 10px 40px;
            background:#009edf;
            color: #FFF;
            text-shadow: none;
            -webkit-box-shadow: 0px 3px 5px 0px rgba(0,0,0,0.31);
            -moz-box-shadow: 0px 3px 5px 0px rgba(0,0,0,0.31);
            box-shadow: 0px 3px 5px 0px rgba(0,0,0,0.31);

        }
        span.input-group-addon i {
            color: #009edf;
            font-size: 17px;
        }

        .login-button{
            margin-top: 5px;
        }

        .login-register{
            font-size: 11px;
            text-align: center;
        }

    </style>
    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

</head>
<body>
<%--<form action="/admin/manualInsertion" method="post"><br/>--%>
    <%--姓名: <input type="text" name="name" required="required" value="name" placeholder="input name"/><br/>--%>
    <%--身份证：<input type="text" name="ID_code" required="required" value="13010419920518241X"/><br/>--%>
    <%--单位内编号：<input type="text" name="sn_in_center" value="110042"/><br/>--%>
    <%--邮箱：<input type="text" name="email" value="xu.yan11@icloud.coms"/><br/>--%>
    <%--系统内唯一编号：<input type="text" name="global_sn" required="required" value="140662"/><br/>--%>
    <%--身份：<input type="radio" name="relative" value=1 checked/>参与人--%>
         <%--<input type="radio" name="relative" value=0 />家属<br/>--%>
    <%--条形码：<input type="text" name="barcode" value="security*&^"/><br/>--%>
    <%--电话1：<input type="text" name="tel1" value="15151528348"/><br/>--%>
    <%--电话2：<input type="text" name="tel2" value="110"/><br/>--%>
    <%--提交：<input type="submit" name="submit">--%>
    <%--<h1 color="red">${requestScope.message}</h1>2--%>
<%--</form>--%>

<%--<div class="signup-form">--%>
    <%--<form action="/admin/manualInsertion" method="post">--%>
        <%--<h2>填写用户数据</h2>--%>
        <%--<p class="hint-text">Create user account</p>--%>
        <%--<div class="form-group has-warning">--%>
            <%--身份证: <input type="text" class="form-control" name="ID_code" placeholder="身份证号码" required="required" value="xu">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--邮箱: <input type="email" class="form-control" name="email" placeholder="邮箱" required="required" value="xu.yan11@icloud.com">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--单位内编号: <input type="text" class="form-control" name="sn_in_center" placeholder="单位内编号" required="required" value="1100420">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--系统内唯一编号: <input type="text" class="form-control" name="global_sn" placeholder="系统内唯一编号" required="required" value="1406626">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--身份: <input type="radio" class="form-control" name="relative" required="required" value=1 checked>参与人--%>
                 <%--<input type="radio" class="form-control" name="relative" required="required" value=2>家属--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--条形码: <input type="text" class="form-control" name="barcode" placeholder="条形码" required="required">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--电话1: <input type="text" class="form-control" name="tel1" placeholder="电话1" required="required">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--电话2: <input type="text" class="form-control" name="tel2" placeholder="电话2" required="required">--%>
        <%--</div>--%>

        <%--<div class="form-group">--%>
            <%--<label class="checkbox-inline"><input type="checkbox" required="required"> I accept the <a href="#">Terms of Use</a> &amp; <a href="#">Privacy Policy</a></label>--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--<button type="submit" class="btn btn-success btn-lg btn-block">Submit</button>--%>
        <%--</div>--%>
    <%--</form>--%>
<%--</div>--%>
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
        <a class="navbar-brand mx-auto" href="#">Navbar</a>
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
                    <li><a href="/user/preferences"><i class="icon-cog"></i> Preferences</a></li>
                    <li><a href="/help/support"><i class="icon-envelope"></i> Contact Support</a></li>
                    <li class="divider"></li>
                    <li><a href="/auth/logout"><i class="icon-off"></i> Logout</a></li>
                </ul>
            </li>
        </ul>
        <%
            }
        %>
    </div>
</nav>
<%--https://bootsnipp.com/snippets/featured/register-page-design--%>
<div class="container">
    <div class="row main">
        <div class="main-login main-center">
            <form action="/admin/manualInsertion" method="post">

                <div class="form-group">
                    <label for="ID_code" class="cols-sm-2 control-label">身份证</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <%--<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>--%>
                            <input type="text" class="form-control" name="ID_code" id="ID_code"  placeholder="Your national ID" required="required"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email" class="cols-sm-2 control-label">邮箱</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <%--<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>--%>
                            <input type="text" class="form-control" name="email" id="email"  placeholder="Enter your Email" required="required"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="sn_in_center" class="cols-sm-2 control-label">单位内编号</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <%--<span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>--%>
                            <input type="text" class="form-control" name="sn_in_center" id="sn_in_center"  placeholder="请输入单位内编号" required="required"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="global_sn" class="cols-sm-2 control-label">系统内编号</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <%--<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>--%>
                            <input type="text" class="form-control" name="global_sn" id="global_sn"  placeholder="请输入系统内编号" required="required"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">

                    <label for="radio120" class="cols-sm-2 control-label">身份</label>
                        <div class="form-check">
                            <input class="form-check-input" name="relative" type="radio" id="radio120" checked="checked" value=1>
                            <label class="form-check-label" for="radio120">参与者</label>
                        </div>

                        <div class="form-check">
                            <input class="form-check-input" name="relative" type="radio" id="radio121" value=0>
                            <label class="form-check-label" for="radio121">家属</label>
                        </div>
                </div>

                <div class="form-group">
                    <label for="barcode" class="cols-sm-2 control-label">条形码</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <%--<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>--%>
                            <input type="text" class="form-control" name="barcode" id="barcode"  placeholder="请输入条形码" required="required"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="tel1" class="cols-sm-2 control-label">tel1</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <%--<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>--%>
                            <input type="text" class="form-control" name="tel1" id="tel1"  placeholder="请输入tel1" required="required"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="tel2" class="cols-sm-2 control-label">tel2</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <%--<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>--%>
                            <input type="text" class="form-control" name="tel2" id="tel2"  placeholder="请输入tel2" required="required"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <input type="submit" class="btn btn-primary btn-lg btn-block login-button" value="submit"/>
                </div>

            </form>
        </div>
    </div>
</div>

<%
    List<Person> persons = (List<Person>) request.getAttribute("persons");
    if (persons != null) {
%>
    <table border="1" width="100%">
        <thead>注册用户数据展示</thead>
        <tr>
            <td>id</td>
            <td>name</td>
            <td>gender</td>
            <td>邮箱</td>
            <td>单位内编号</td>
            <td>系统内唯一编号</td>
            <td>身份</td>
            <td>条形码</td>
            <td>tel1</td>
            <td>tel2</td>
            <td>数据库操作</td>
        </tr>
        <%for (Person p:persons){%>
        <tr>
            <td><%= p.getIdperson()%></td>
            <td><%= p.getName()%></td>
            <td><%= p.getGender()%></td>
            <td><%= p.getEmail()%></td>
            <td><%= p.getSn_in_center()%></td>
            <td><%= p.getGlobal_sn()%></td>
            <td><%= p.getRelative()%></td>
            <td><%= p.getBarcode()%></td>
            <td><%= p.getTel1()%></td>
            <td><%= p.getTel2()%></td>
            <td><a href="/admin/updatePerson?idperson=<%=p.getIdperson()%>">更新</a> |
                <a href="/admin/delete?idperson=<%=p.getIdperson()%>">删除</a> </td>
        </tr>
            <%}%><%--persons--%>
    </table>
    <%}%><%--if persons not null--%>

<!--测试用-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

<%--todo: validate input fields--%>
<script type="text/javascript">
</script>
</body>
</html>