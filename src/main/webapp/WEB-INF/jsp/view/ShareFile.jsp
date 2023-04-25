<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<jsp:include page="Header.jsp" />

<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <script src="lib/jquery.min.js"></script>
    <script src="js/ManageFile.js"></script>
</head>

<body>
    <form action="UploadShareFile" method="POST" enctype="multipart/form-data">
        ${resultFile}
        <input type="file" name="resultFile"/>
        <input type="submit" value="上傳" />
    </form>
    <br>
    <table class="table table-hover">
        <span id="fileNameList" style="display: none;">${fileNameList}</span>
    </table>

    <button id="return" onclick="location.href = 'ResetFileList';">重置</button>
</body>
</html>
<jsp:include page="MeetingFooter.jsp" />