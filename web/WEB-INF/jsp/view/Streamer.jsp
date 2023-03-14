<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<jsp:include page="Header.jsp" />

<div>
  <button id="startBtn">開始</button>
  <button id="endBtn">結束分享</button>
</div>

<div class="table-card">
  <video autoplay playsinline id="localVideo"></video>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/4.0.1/socket.io.js"></script>
<script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
<script src="../../js/Streamer.js"></script>


<jsp:include page="MeetingFooter.jsp" />