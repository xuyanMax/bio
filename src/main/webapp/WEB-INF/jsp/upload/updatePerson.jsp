<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 29/05/2018
  Time: 01:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Personal data</title>
</head>
<body>
        <% if (request.getAttribute("person") != null){
        %>
       <%-- <form method="post" action="/admin/update"></form>
        age: <input type="text" value="${person.age}"/> <br/>
        gender:<input type="text" value="${person.gender}"/> <br/>
        email:<input type="text" value="${person.email}"/><br/>
        sn_in_center:<input type="text" value="${person.sn_in_center}"/><br/>
        global_sn:<input type="text" value="person.global_sn"/><br/>
        tel1:<input type="text" value="${person.tel1}"/><br/>
        barcode:<input type="text" value="${person.barcode}"/><br/>
        relative:<input type="text" value="${person.relative}"/><br/>
        submit: <input type="submit" value="update">--%>

        <form action="/admin/update" method="post">
                <h2>Update</h2>
                <p class="hint-text">Create your personal account</p>
                <div class="form-group has-warning">
                        身份证: <input type="text" class="form-control" name="ID_code" placeholder="身份证号码" required="required" value="xu">
                </div>
                <div class="form-group">
                        邮箱: <input type="email" class="form-control" name="email" placeholder="邮箱" required="required" value="xu.yan11@icloud.com">
                </div>
                <div class="form-group">
                        单位内编号: <input type="text" class="form-control" name="sn_in_center" placeholder="单位内编号" required="required" value="1100420">
                </div>
                <div class="form-group">
                        系统内唯一编号: <input type="text" class="form-control" name="global_sn" placeholder="系统内唯一编号" required="required" value="1406626">
                </div>
                <div class="form-group">
                        身份: <input type="radio" class="form-control" name="relative" required="required" value=1 checked>参与人
                        <input type="radio" class="form-control" name="relative" required="required" value=2>家属
                </div>
                <div class="form-group">
                        条形码: <input type="text" class="form-control" name="barcode" placeholder="条形码" required="required">
                </div>
                <div class="form-group">
                        电话1: <input type="text" class="form-control" name="tel1" placeholder="电话1" required="required">
                </div>
                <div class="form-group">
                        电话2: <input type="text" class="form-control" name="tel2" placeholder="电话2" required="required">
                </div>

                <%--<div class="form-group">--%>
                        <%--<label class="checkbox-inline"><input type="checkbox" required="required"> I accept the <a href="#">Terms of Use</a> &amp; <a href="#">Privacy Policy</a></label>--%>
                <%--</div>--%>
                <div class="form-group">
                        <button type="submit" class="btn btn-success btn-lg btn-block">Submit</button>
                </div>
        </form>
        <%
            }
        %>

</body>
</html>
