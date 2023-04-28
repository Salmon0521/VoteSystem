function viewBallot() {
    $.ajax({
        type : 'POST',
        url : 'CheckVoting',
        data : {
            "MeetingType" : this.id,
        },
        success: function (data) {
            if (data === "0") {
                alert("尚未上傳選票檔!");
            } else if (data === "1") {
                alert("已開票，故無法投票!");
            } else if (data === "2") {
                alert("已完成投票，故無法重複投票!");
            } else {
                window.location.href = "BallotPage";
            }
        },
    });
}