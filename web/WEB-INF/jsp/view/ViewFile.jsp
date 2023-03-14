<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="VoteHeader.jsp" />

<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../../js/File.js"></script>
</head>

<body>
    <table class="table table-hover">
        <c:forEach var="file" items="${fileNameList}">
            <tr>
                <td>
                    <a>${file}</a>
                    <button class="span-right" onclick="Download('${file}')">下載</button>
                    <br>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
<jsp:include page="MeetingFooter.jsp" />