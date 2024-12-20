<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <script src="js/ManageBallot.js"></script>
    <link rel="stylesheet" href="css/index.css" />
</head>

<body class="background">
    <div class="container text-center d-flex justify-content-center center flex-column">
        <div class="mb-5">
            <label id="votingActivityTitle" class="fs-label">投票活動標題:</label>
            <div class="fs-label">已投票人數: <font id="count">0</font></div>
        </div>
        <div>
            <button class="btn btn-secondary" id="invoicing" onclick="invoicing()">開票</button>
            <button class="btn btn-danger" id="reset" onclick="reset()">重置</button>
        </div>
    </div>
</body>
</html>