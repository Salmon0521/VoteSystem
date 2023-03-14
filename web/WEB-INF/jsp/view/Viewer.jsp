<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="VoteHeader.jsp" />

<div>
  <button id="startBtn">查看</button>
</div>

<div class="table-card">
  <video autoplay playsinline id="remoteVideo" muted="muted"></video>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/4.0.1/socket.io.js"></script>
<script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
<script src="../../js/Viewer.js"></script>

<jsp:include page="MeetingFooter.jsp" />
