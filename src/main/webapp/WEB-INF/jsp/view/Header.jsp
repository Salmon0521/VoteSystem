<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>投票系統</title>
        <link rel="stylesheet" href="lib/bootstrap/dist/css/bootstrap.min.css" />
        <script src="lib/jquery.min.js"></script>
        <script src="lib/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <header>
                <div class="container">
                    <a href="Index">投票系統</a>
                    <div>
                        ${sessionScope.account}
                        <a href="Logout">登出</a>
                    </div>
                </div>
        </header>




