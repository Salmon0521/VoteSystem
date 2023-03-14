<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/4.0.1/socket.io.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../../js/VoteManage.js"></script>
</head>

<body>
    <div class="loadingdiv" id="loading" style="display: none">
        <img src="../../img/loading.gif" />
    </div>
    <p>${title}</p>
    <table class="table">
        <tr>
            <th>當前選票檔：<font id="ballotFileName">${ballotFile}</font></th>
            <th>當前結果檔：<font id="resultFileName">${resultFile}</font></th>
            <th>已投票人數：<font id="count"></font></th>
            <th hidden><font id="status">${status}</font></th>
        </tr>
    </table>
    <form id="${meetingType}" class="Upload" action="/Upload" method="POST" enctype="multipart/form-data">
        <table class="table" id="updateTable">
            <tr>
                <td>選票檔</td>
                <td><input type="file" name="ballotFile" required/></td>
            </tr>
            <tr>
                <td>結果檔</td>
                <td><input type="file" name="resultFile" required/></td>
            </tr>

            <tr>
                <td></td>
                <td>
                    <a id="professor1" href="/DownloadExampleFiles?DownloadType=0&MeetingType=${meetingType}" target="_blank"><img src="../../img/ExampleFileIcon_small.png" border="0">教授_選票檔範例</a>
                    <a id="professor2" href="/DownloadExampleFiles?DownloadType=1&MeetingType=${meetingType}" target="_blank"><img src="../../img/ExampleFileIcon_small.png" border="0">兼任教師_選票檔範例</a>
                    <a id="general" href="/DownloadExampleFiles?DownloadType=0&MeetingType=${meetingType}" target="_blank"><img src="../../img/ExampleFileIcon_small.png" border="0">選票檔範例</a>
                    <a href="/DownloadExampleFiles?DownloadType=2&MeetingType=${meetingType}" target="_blank"><img src="../../img/ExampleFileIcon_small.png" border="0">結果檔範例</a>
                </td>
                <td style="vertical-align:text-top;">
                    <input type="hidden" id="MeetingType" name="MeetingType" value="${meetingType}">
                    <input style="width:6em" type="submit" value="創立投票">
                </td>
            </tr>
        </table>
    </form>
    <div class="invoicing-div">
        <button class="invoicing-btn" id="invoicing" onclick="invoicing('${meetingType}')">開票</button>
        <button class="reset-btn" id="reset" onclick="reset()">重置</button>
    </div>
    <br>
    <div class="invoicing-div" id="total">
        <form id="InvoicingOne" class="Invoicing" method="POST" action="/Invoicing">
            <input type="submit" class="total-btn" value="統計結果"></input>
            <input type="hidden" name="MeetingType" value="${meetingType}">
            <input type="hidden" name="DownloadType" value="0">
        </form>

        <br>

        <form id="InvoicingTwo" class="Invoicing" method="POST" action="/Invoicing">
            <input type="submit" class="total-btn" value="各別結果"></input>
            <input type="hidden" name="MeetingType" value="${meetingType}">
            <input type="hidden" name="DownloadType" value="1">
        </form>
    </div>

</body>
</html>
<jsp:include page="MeetingFooter.jsp" />