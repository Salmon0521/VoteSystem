<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <script src="lib/jquery.min.js"></script>
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
                <img src="images/1.jpg" width="100" height="100">
                <br>候選人1
            </td>
            <td>候選人1簡介</td>
        </tr>
        <tr>
            <td>
                <input id="candidate0" type="radio" value="1" name="radioBox">
            </td>
            <td><img src="images/2.jpg" width="100" height="100">
                <br>候選人2
            </td>
            <td>候選人2簡介</td>
        </tr>
    </table>
    <button type="button" onclick="sendBallot()">送出選票</button>
</body>
</html>