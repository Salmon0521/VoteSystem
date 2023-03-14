let MeetingType
$(function() {
    MeetingType = document.getElementById("MeetingType").value
    if (MeetingType === "Assessment") {
        $("#general").hide();
    } else {
        $("#professor1").hide();
        $("#professor2").hide();
    }
    $("#total").hide();
    $("#invoicing").hide();
    $("#reset").hide();
    checkFileExist();
    connectIO();
    getCount();

    init();
});

function connectIO() {
    console.log( "Start listening!" );
    socket = io('ws://140.124.181.20:8088');

    socket.on('updateVoteMsg', async (meetingType, count) => {
        console.log( meetingType +" "+ count + " " + MeetingType);
        if (MeetingType == meetingType){
            document.getElementById("count").innerHTML = count;
        }
    })

}

function checkFileExist() {
    let ballotFileName = document.getElementById("ballotFileName").innerHTML
    let resultFileName = document.getElementById("resultFileName").innerHTML
    if ( !((ballotFileName == null || ballotFileName == "") && (resultFileName == null || resultFileName == "")) ){
        $("#updateTable").hide();
        $("#reset").show();
        checkInvoicing();
    }
}

function invoicing(MeetingType){
    $.ajax({
        type: "POST",
        url: "/ChangeStatus",
        data : {
            "MeetingType" : MeetingType
        },
        success: function (data) {
            if (data === "1") {
                alert("尚未擁有已投票之選票!");
            } else {
                window.location.reload();
            }
        },
    });
}

function checkInvoicing(){
    let status = document.getElementById("status").innerHTML
    if ( status == "true" ){
        $("#invoicing").show();
    }else{
        $("#invoicing").hide();
        $("#total").show();
    }
}

function getCount(){
    $.ajax({
        type : 'GET',
        url : '/GetCount?MeetingType='+MeetingType,
        success: function (count) {
            document.getElementById("count").innerHTML = count;
        },
    });
}

function reset() {
    let reset = confirm("此操作會移除所有上傳檔案及投票紀錄");
    if (reset) {
        document.getElementById('updateTable').style.display = 'table';
        $.ajax({
            type : 'post',
            url : '/Reset',
            data : {
                "MeetingType" : MeetingType
            },

            success: function () {
                alert("重置成功!");
                window.location.reload();
            },
        });
    }
}

function init(){
    if(MeetingType == "Assessment"){
        $("#InvoicingTwo").hide();
    }
    $("form.Invoicing").submit(function (e) {
        e.preventDefault();
        $.ajax({
            type: this.method,
            url: this.action,
            data: $("#" + this.id).serialize(),

            success: function (data) {
                if (data === "1") {
                    alert("匯出檔案發生錯誤!");
                } else {
                    window.open(data);
                }
            },
        });
    });

    $("form.Upload").submit(function (e) {
        e.preventDefault();

        let formId = this.id;
        let formData = new FormData();
        formData.append("MeetingType", formId);
        let fileInput = $("input[type='file']");
        for (let i=0; i<fileInput.length; i++) {
            formData.append("file[" + i + "]", $("input[type='file']")[i].files[0]);
        }

        $('#loading').css("display", "");
        $.ajax({
            type: this.method,
            url: this.action,
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType : false,

            success: function (data) {
                if (data === "1") {
                    alert("上傳成功!");
                } else {
                    if (data === "2") {
                        alert("選票檔格式有誤!");
                    } else if (data === "3") {
                        alert("結果檔格式有誤!");
                    }
                    for (let i=0; i<fileInput.length; i++) {
                        $("input[type='file']")[i].value = "";
                    }
                }
                $('#loading').css("display", "none");
                window.location.href = "/UploadAndStatistic?MeetingType=" + formId;
            },
        });
    });
}