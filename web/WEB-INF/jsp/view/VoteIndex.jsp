<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="VoteHeader.jsp" />

<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../../js/VoteIndex.js"></script>
</head>

<div class="table-card">
    ${userUUID} 委員 您好
    <table class="table table-hover">
        <tr>
            <td>
                <a class="ballots" id="AssessmentBallot">
                    <div>
                        + 教評會評分
                        <span class="span-right">
                            <i class="arrow-right"></i>
                        </span>
                    </div>
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <a class="ballots" id="AgreementBallot">
                    <div>
                        + 同意不同意
                        <span class="span-right">
                            <i class="arrow-right"></i>
                        </span>
                    </div>
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <a class="ballots" id="RankBallot">
                    <div>
                        + 序位
                        <span class="span-right">
                            <i class="arrow-right"></i>
                        </span>
                    </div>
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="/ViewFile">
                    <div>
                        + 下載檔案
                        <span class="span-right">
                            <i class="arrow-right"></i>
                        </span>
                    </div>
                </a>
            </td>
        </tr>

        <tr>
            <td>
                <a href="/ViewScreen">
                    <div>
                        + 查看分享螢幕
                        <span class="span-right">
                            <i class="arrow-right"></i>
                        </span>
                    </div>
                </a>
            </td>
        </tr>
    </table>
</div>
</html>
<jsp:include page="MeetingFooter.jsp" />