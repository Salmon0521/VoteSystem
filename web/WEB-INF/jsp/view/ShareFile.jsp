<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="Header.jsp" />

<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../../js/File.js"></script>
</head>

<body>
    <form action="/UploadShareFile" method="POST" enctype="multipart/form-data">
        ${resultFile}
        <input type="file" name="resultFile"/>
        <input type="submit" value="上傳" />
    </form>
    <br>
    <table class="table table-hover">
        <c:forEach var="file" items="${fileNameList}">
            <tr>
                <td>
                    <a>${file}</a>
                    <button class="span-right" onclick="location.href = '/DeleteSingleFile?Filename=${file}';">刪除</button>
                    <button class="span-right" onclick="Download('${file}')">下載</button>
                    <br>
                </td>
            </tr>
        </c:forEach>
    </table>

    <button id="return" onclick="location.href = '/ResetFileList';">重置</button>
</body>
</html>
<jsp:include page="MeetingFooter.jsp" />