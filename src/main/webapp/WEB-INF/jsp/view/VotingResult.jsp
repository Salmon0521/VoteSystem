<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <link rel="stylesheet" href="css/index.css" />
    <script src="js/Result.js"></script>
</head>

<body class="background">
    <div class="container mt-5 d-flex text-center flex-column">
        <div class="mb-5 d-flex row">
            <label id="votingActivityTitle" class="fs-label col-3">投票活動標題:</label>
            <div id="votingRate" class="fs-label col-3">當前投票率：<font >0%</font></div>
            <div id="count" class="fs-label col-3">當前投票數：<font >0</font></div>
            <div id="totalVoting" class="fs-label col-3">總投票數：<font>0</font></div>
        </div>
        <div>
            <div id="ballot" class="fs-label"></div>
        </div>
        <h1>票選結果</h1>
        <table id="ResultTable">
        </table>
    </div>
    <div class="d-flex justify-content-center">
        <button class="btn btn-secondary" id="download" style="display: none;" onclick="alert('下載功能尚未開發')">下載</button>
    </div>
</body>
</html>