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
      <table class="table">
        <tr class="row text-center">
          <th class="col">勾選：</th>
          <th class="col">候選人：</th>
        </tr>
        <tr class="row">
          <td class="col d-flex justify-content-center">
            <input id="candidate0" type="radio" value="0" name="radioBox" class="ballotInput"/>
          </td>
          <td class="col d-flex flex-column align-itmes-center text-center">
            <!-- Card Flip -->
            <div class="card-flip">
              <div class="flip">
                <div class="front d-flex justify-content-center">
                  <!-- front content -->
                  <div class="card cardSize">
                    <img src="img/dragon.png" />
                  </div>
                </div>
                <div class="back d-flex justify-content-center">
                  <!-- back content -->
                  <div class="card cardSize">候選人簡介</div>
                </div>
              </div>
            </div>
            <!-- End Card Flip -->
            <br />候選人1
          </td>
        </tr>
        <tr class="row">
          <td class="col d-flex justify-content-center">
            <input id="candidate0" type="radio" value="1" name="radioBox" class="ballotInput"/>
          </td>
          <td class="col d-flex flex-column align-itmes-center text-center">
            <!-- Card Flip -->
            <div class="card-flip">
              <div class="flip">
                <div class="front d-flex justify-content-center">
                  <!-- front content -->
                  <div class="card cardSize">
                    <img src="img/turtle.png" />
                  </div>
                </div>
                <div class="back d-flex justify-content-center">
                  <!-- back content -->
                  <div class="card cardSize">候選人簡介</div>
                </div>
              </div>
            </div>
            <!-- End Card Flip -->
            <br />候選人2
          </td>
        </tr>
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