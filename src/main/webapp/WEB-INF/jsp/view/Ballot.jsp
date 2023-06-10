<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <script src="js/Ballot.js"></script>
    <link rel="stylesheet" href="css/index.css" />
</head>

 <body class="background">
    <div class="container d-flex align-items-center flex-column mb-5">
        <label id="votingActivityTitle" class="fs-label">投票活動標題:</label>
        <table class="table">
        </table>
        <button
        type="button"
        onclick="sendBallot()"
        class="btn btn-danger fs-label"
        >
        送出選票
        </button>
    </div>
    <script>
      document.querySelector(".card-flip").classList.toggle("flip");
    </script>
  </body>
</html>