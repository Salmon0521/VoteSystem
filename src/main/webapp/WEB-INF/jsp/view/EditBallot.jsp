<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <script src="js/EditBallot.js"></script>
    <link rel="stylesheet" href="css/index.css" />
</head>

<body class="background">
    <div class="container mt-5 d-flex text-center flex-column">
        <table id="candidateTable" class="mb-4">
        </table>
        <form class="mb-4" action="AddCandidate" method="POST" encType="multipart/form-data">
            <table id="newCandidateTable" class="w-100">
            </table>
        </form>
        <div class="d-flex justify-content-center">
            <button class="mr-3 btn btn-primary" type="button" onclick="addCandidate()">新增候選人</button>
            <button class="mr-3 btn btn-success" type="button" onclick="createVoteActivity()">建立投票活動</button>
        </div>
    </div>
</body>
</html>