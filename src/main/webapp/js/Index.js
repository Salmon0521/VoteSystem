function viewBallot() {
    $.ajax({
        type : 'POST',
        url : 'CheckVoting',
        data : {
            "MeetingType" : this.id,
        },
        success: function (data) {
            if (data === "0") {
                alert("投票活動尚未舉辦!");
            } else if (data === "1") {
                alert("已完成投票!");
            } else {
                window.location.href = "BallotPage";
            }
        },
    });
}

function createActivity() {
    $.ajax({
        type : 'POST',
        url : 'CheckVoteActivity',
        data : {

        },
        success: function (data) {
            if (data === "0") {
                window.location.href = "EditBallot";
            } else if (data === "1") {
                window.location.href = "ManageVoteActivity";
            }
        },
    });
}