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
            <div class="fs-label text-center">創立投票活動</div>
            <button class="button createVotingActivity" id="createActivity" onclick="editCandidate()"></button>
        </div>
        <div>
            <div class="fs-label text-center">查看投票結果</div>
            <button class="button report" id="ballotCondition"></button>
        </div>
    </div>
</body>
</html>