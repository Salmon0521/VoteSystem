<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <script src="js/BallotCount.js"></script>
</head>

<body>
<th>已投票人數：<font id="count">0</font></th>
<button class="invoicing-btn" id="invoicing" onclick="invoicing('${meetingType}')">開票</button>
<button class="reset-btn" id="reset" onclick="reset()">重置</button>
</body>
</html>