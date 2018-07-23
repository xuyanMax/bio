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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://surveyjs.azureedge.net/1.0.29/survey.jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.29/survey.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
    <div id="surveyContainer"></div>

    <script type="text/javascript">
        Survey.Survey.cssType = "bootstrap";
        var surveyJSON = ${surveyJSON};
        var survey = new Survey.Model(surveyJSON);
        function sendDataToServer(survey) {
            //send Ajax request to your web server.
            $.ajax({
                type:"POST",
                url:"/user/surveyUpload",
                // contentType:"application/json",
                dataType:"json",
                data:surveyJSON,
                success:alert("问卷结果：" + JSON.stringify(survey.data))
            });
            // alert("The results are:" + JSON.stringify(survey.data));
        }
        $("#surveyContainer").Survey({
            model: survey,
            onComplete: sendDataToServer
        });
    </script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>
</html>