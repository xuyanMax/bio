<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 2018/7/2
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>调查问卷</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
    <script src="https://surveyjs.azureedge.net/1.0.29/survey.jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.29/survey.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
    <div id="surveyContainer"></div>

    <script type="text/javascript">

        $(document).ready(function () {
            Survey.Survey.cssType = "bootstrap";
            var surveyJSON = ${surveyJSON};
            var survey = new Survey.Model(surveyJSON);
            $("#surveyContainer").Survey({
                model: survey,
                onComplete: sendDataToServer
            });
            function sendDataToServer(survey) {
                //send Ajax request to your web server.
                $.ajax({
                    type:"POST",
                    url:"/process/survey",
                    // contentType:"application/json",
                    dataType:"json",
                    data:survey.data,
                    success:function (){
                        alert("问卷结果：" + JSON.stringify(survey.data));
                        // alert(surveyJSON);
                        // alert(JSON.stringify(surveyJSON));
                    }
                });
            }

        });
        // sendDataToServer(survey);
        // var complete = new Survey.Survey(json)

    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>
</html>
