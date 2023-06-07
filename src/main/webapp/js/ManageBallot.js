let interval;

function reset() {
    let reset = confirm("此操作會移除所有候選人資訊及投票紀錄");
    if (reset) {
        $.ajax({
            type : 'POST',
            url : 'Reset',
            data : {},
            success: function () {
                alert("重置成功!");
                window.location.href="Index";
            },
        });
    }
}

function invoicing(){
    let invoice = confirm("確定進行開票？");
    if (invoice) {
        $.ajax({
            type: "POST",
            url: "Invoicing",
            data : {},
            success: function (response) {
                if(response === "0"){
                    alert("投票活動開票成功!");
                    window.location.href="Index";
                }
                else if(response === "1"){
                    alert("投票活動已開票");
                    window.location.href="Index";
                }
            },
        });
    }
}

function showTitle() {
    $.ajax({
        url : "GetTitle",
        type : "POST",
        data: {},
        dataType : "json",
        success : function(response) {
            $("#votingActivityTitle").text("投票活動標題: " + response);
        }
    });
}

function countBallot() {
    interval = setInterval(function(){
        $.ajax({
            type : 'POST',
            url : 'CountBallot',
            success: function (response) {
                let data = JSON.parse(response);
                document.getElementById("count").innerHTML = data["countNum"];
            },
        });
    }, 200);
}

clearInterval(interval)

function init() {
    showTitle();
    countBallot();
}

$(document).ready(function(){
    init();
});
