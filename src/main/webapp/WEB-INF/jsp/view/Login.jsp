<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>投票系統</title>
    <script src="lib/jquery.min.js"></script>
    <script src="lib/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/Login.js"></script>
</head>

<body>
    <form>
        <div>
            <label class="form-label">帳號:</label>
            <input type="text" id="account">
        </div>
        <div>
            <label class="form-label">密碼:</label>
            <input type="text" id="password">
        </div>
        <button type="button" onclick="login()">登入</button>
    </form>
</body>
</html>