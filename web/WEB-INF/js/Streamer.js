// video 標籤
const localVideo = document.querySelector('video#localVideo')

let localStream
let PCs = {}
let socket

const room = 'room1'

// ===================== 連線相關 =====================
/**
 * 連線 socket.io
 */
function connectIO() {
  // socket
  socket = io('ws://localhost:8088')


  socket.on('ice_candidate', async (data, id) => {
    console.log('收到 ice_candidate')
    const candidate = new RTCIceCandidate({
      sdpMLineIndex: data.label,
      candidate: data.candidate,
    })
    if (id in PCs)
        await PCs[id].addIceCandidate(candidate)
  })

  socket.on('offer', async (desc, id) => {
    console.log('收到 offer')
    const pc = initPeerConnection()
    PCs[id] = pc
    // 設定對方的配置
    await pc.setRemoteDescription(desc)

    // 發送 answer
    await sendSDP(false, pc)
  })

  socket.on('bye', async (id) => {
    console.log(id, '中斷連線')
    delete PCs[id]
  })

  socket.emit('join', room)
}

/**
 * 取得本地串流
 */
async function createStream() {
  try {
    const constraints = {
      // audio: true,
      video: true,
    }
    const stream = await navigator.mediaDevices.getDisplayMedia(constraints)

    // somebody clicked on "Stop sharing"
    stream.getVideoTracks()[0].onended = function () {
        PCs = {};
        localVideo.pause();
        localVideo.srcObject = null;
        localVideo.style.display = "none";

        localStream.getTracks()
          .forEach(track => track.stop())
    };

    localStream = stream

    localVideo.srcObject = stream
  } catch (err) {
    throw err
  }
}

/**
 * 初始化Peer連結
 */
function initPeerConnection() {
  const configuration = {
    iceServers: [
      {
        urls: 'stun:stun.l.google.com:19302',
      },
    ],
  }
  let peerConn = new RTCPeerConnection(configuration)

  // 增加本地串流
  localStream.getTracks().forEach((track) => {
    peerConn.addTrack(track, localStream)
  })

  // 找尋到 ICE 候選位置後，送去 Server 與另一位配對
  peerConn.onicecandidate = (e) => {
    if (e.candidate) {
      console.log('發送 ICE')
      // 發送 ICE
      socket.emit('ice_candidate', room, {
        label: e.candidate.sdpMLineIndex,
        id: e.candidate.sdpMid,
        candidate: e.candidate.candidate,
      })
    }
  }

  // 監聽 ICE 連接狀態
  peerConn.oniceconnectionstatechange = (e) => {
    if (e.target.iceConnectionState === 'disconnected') {
      console.log("remote disconnected");

    }
  }

  return peerConn
}

/**
 * 處理信令
 * @param {Boolean} isOffer 是 offer 還是 answer
 */
async function sendSDP(isOffer, pc) {
  try {
    if (!pc) {
      initPeerConnection()
    }

    // 創建SDP信令
    const localSDP = await pc[isOffer ? 'createOffer' : 'createAnswer']({
      offerToReceiveAudio: true, // 是否傳送聲音流給對方
      offerToReceiveVideo: true, // 是否傳送影像流給對方
    })

    // 設定本地SDP信令
    await pc.setLocalDescription(localSDP)

    // 寄出SDP信令
    let e = isOffer ? 'offer' : 'answer'
    socket.emit(e, room, pc.localDescription)
  } catch (err) {
    throw err
  }
}


/**
 * 初始化
 */
async function init() {
//    document.getElementById("localVideo").hidden = false;
    document.getElementById("localVideo").style.display = "";
    await createStream()
    connectIO()
}

/**
 * 結束分享
 */
async function end() {
    console.log("Closing connection call");
    Object.keys(PCs).forEach(function(key){
        let peer = PCs[key];
        if (!peer) return;

        // Stop all tracks on the connection
        peer.getSenders().forEach((sender) => {
            peer.removeTrack(sender);
        });

        // Close the peer connection

        peer.close();
        peer = null;
        cacheStream = null;
        dataChannel = null;
    });
    PCs = {};
    localVideo.pause();
    localVideo.srcObject = null;
    localVideo.style.display = "none";

    localStream.getTracks()
      .forEach(track => track.stop());

    socket.emit('disconnect_streamer', room);
}


// ===================== 監聽事件 =====================
/**
 * 監聽按鈕點擊
 */
startBtn.onclick = init
endBtn.onclick = end


