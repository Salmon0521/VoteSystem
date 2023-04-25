<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="VoteHeader.jsp" />

<div>
  <button id="startBtn" style="display: none;">查看</button>
</div>

<div class="table-card">
  <video autoplay playsinline id="remoteVideo" muted="muted"></video>
</div>

<script src="lib/socket.io.js"></script>
<script src="lib/adapter-latest.js"></script>
<script src="js/Viewer.js"></script>

<jsp:include page="MeetingFooter.jsp" />
