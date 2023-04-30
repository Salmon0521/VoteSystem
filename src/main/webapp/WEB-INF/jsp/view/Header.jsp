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
                <div class="container d-flex py-2 justify-content-between align-items-center">
                    <a href="Index" class="text-decoration-none text-dark fs-label">投票系統</a>
                    <div class="d-flex align-items-center fs-label">
                        <div style="margin-right:1rem">
                            歡迎,
                            ${sessionScope.account}
                        </div>
                        <a href="Logout"><button class="btn btn-dark fs-label">登出</button></a>
                    </div>
                </div>
        </header>




