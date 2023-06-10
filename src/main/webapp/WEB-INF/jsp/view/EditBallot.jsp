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
    <div class="container mt-5 d-flex text-left flex-column">
        <div class="mb-5 row">
            <label id="votingActivityTitle" class="fs-label col-3">投票活動標題:</label>
            <div id="addTitle" style="display: none;">
                <input type="text" id="title">
                <button class="mr-3 btn btn-primary" type="button" onclick="addVotingActivityTitle()">新增標題</button>
                <button class="mr-3 btn btn-primary" type="button" onclick="updateVotingActivityTitle()">取消</button>
            </div>
            <button class="mr-3 btn btn-primary" id="reviseTitle" type="button" onclick="updateVotingActivityTitle()">修改標題</button>
        </div>
        <table id="candidateTable" class="mb-3">
        </table>
        <form class="mb-5" action="AddCandidate" method="POST" encType="multipart/form-data">
            <table id="newCandidateTable" class="w-100">
            </table>
        </form>
        <div class="d-flex justify-content-left">
            <button class="mr-3 btn btn-primary" type="button" onclick="addCandidate()">新增候選人</button>
            <button class="mr-3 btn btn-success" type="button" onclick="createVoteActivity()">建立投票活動</button>
        </div>
    </div>
</body>
</html>