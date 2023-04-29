<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <link rel="stylesheet" href="css/index.css" />
    <script src="js/Index.js"></script>
</head>

<body class="background">
    <div class="wrap">
        <div>
            <div class="fs-label text-center">投票</div>
            <button class="button vote" id="viewBallot" onclick="viewBallot()"><img src="img/vote_2.png"></button>
        </div>
        <div>
            <div class="fs-label text-center">查看投票結果</div>
            <button class="button vote" id="ballotCondition"><img src="img/report_1.png"><</button>
        </div>
    </div>
</body>
</html>