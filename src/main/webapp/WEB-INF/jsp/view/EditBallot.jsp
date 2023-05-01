<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <script src="js/EditBallot.js"></script>
</head>

<body>
    <table id="candidateTable">
    </table>
    <form action="AddCandidate" method="POST" encType="multipart/form-data">
        <table id="newCandidateTable">
        </table>
    </form>
    <button type="button" onclick="addCandidate()">新增候選人</button>
    <button type="button" onclick="createVoteActivity()">建立投票活動</button>
</body>
</html>