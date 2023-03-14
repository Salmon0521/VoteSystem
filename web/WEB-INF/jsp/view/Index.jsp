<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<div class="table-card">
    管理員 您好
    <table class="table table-hover">
        <tr>
            <td>
                <a href="/UploadAndStatistic?MeetingType=Assessment">
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
                <a href="/UploadAndStatistic?MeetingType=Agreement">
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
                <a href="/UploadAndStatistic?MeetingType=Rank">
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
                <a href="/ShareFile">
                    <div>
                        + 分享檔案
                        <span class="span-right">
                            <i class="arrow-right"></i>
                        </span>
                    </div>
                </a>
            </td>
        </tr>

        <tr>
            <td>
                <a href="/ShareScreen">
                    <div>
                        + 分享螢幕
                        <span class="span-right">
                            <i class="arrow-right"></i>
                        </span>
                    </div>
                </a>
            </td>
        </tr>
    </table>
</div>

<jsp:include page="MeetingFooter.jsp" />