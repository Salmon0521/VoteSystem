<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<jsp:include page="VoteHeader.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <script src="lib/jquery.min.js"></script>
    <script src="js/ShowFile.js"></script>
</head>

<body>
    <table class="table table-hover">
        <span id="fileNameList" style="display: none;">${fileNameList}</span>
    </table>
</body>
</html>
<jsp:include page="MeetingFooter.jsp" />