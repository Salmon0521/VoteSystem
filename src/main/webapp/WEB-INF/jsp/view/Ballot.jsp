<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <script src="js/Ballot.js"></script>
</head>

<body>
    <table class="table">
        <tr>
            <th>勾選：</th>
            <th>候選人：</th>
            <th>候選人簡介：</th>
        </tr>
        <tr>
            <td>
                <input id="candidate0" type="radio" value="0" name="radioBox">
            </td>
            <td>
                <img src="img/candidate.png" width="100" height="100">
                <br>候選人1
            </td>
            <td>候選人1簡介</td>
        </tr>
        <tr>
            <td>
                <input id="candidate1" type="radio" value="1" name="radioBox">
            </td>
            <td><img src="img/candidate.png" width="100" height="100">
                <br>候選人2
            </td>
            <td>候選人2簡介</td>
        </tr>
    </table>
    <button type="button" onclick="sendBallot()">送出選票</button>
</body>
</html>