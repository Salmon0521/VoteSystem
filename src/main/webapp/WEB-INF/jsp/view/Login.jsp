<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>投票系統</title>
    <script src="lib/jquery.min.js"></script>
    <script src="lib/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/Login.js"></script>
    <link rel="stylesheet" href="css/index.css" />

</head>


<body class="background">
    <div class="container Login">
        <h3 class="mb-5">VotingSystem</h3>
        <form class="LoginForm">
            <div class="mb-3">
                <label class="fs-label">帳號:</label>
                <input type="text" id="account" class="form-control">
            </div>
            <div class="mb-3">
                <label class="fs-label">密碼:</label>
                <input type="text" id="password" class="form-control">
            </div>
            <button type="button" onclick="login()" class="btn btn-primary fs-label">登入</button>
        </form>
    </div>
</body>
</html>



