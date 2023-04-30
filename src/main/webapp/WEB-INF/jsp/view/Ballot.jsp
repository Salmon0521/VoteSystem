<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <script src="js/Ballot.js"></script>
    <link rel="stylesheet" href="css/index.css" />
</head>

<body>
        <div class="container">
        <table class="table">
            <tr>
                <th>勾選：</th>
                <th>候選人：</th>
            </tr>
            <tr>
                <td>
                    <input id="candidate0" type="radio" value="0" name="radioBox">
                </td>
                <td>
                    <!-- Card Flip -->
                    <div class="card-flip">
                        <div class="flip">
                            <div class="front">
                                <!-- front content -->
                                <div class="card cardSize">
                                    <img src="img/dragon.png"/>
                                </div>
                            </div>
                            <div class="back">
                                <!-- back content -->
                                <div class="card">
                                  卡片背面
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End Card Flip -->
                    <br>候選人1
                </td>
            </tr>
            <tr>
                <td>
                    <input id="candidate1" type="radio" value="1" name="radioBox">
                </td>
                <td><img src="img/candidate.png" width="100" height="100">
                    <br>候選人2
                </td>
            </tr>
        </table>
            <button type="button" onclick="sendBallot()" class="btn btn-danger fs-label">送出選票</button>
        </div>
    <script>document.querySelector(".card-flip").classList.toggle("flip");</script>
    </body>
</html>