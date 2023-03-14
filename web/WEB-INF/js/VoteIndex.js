window.onload = function() {
    let queryString = window.location.search;
    if (queryString.length !== 0) {
        alert("投票已重置!");
        window.location.href = "VoteIndex";
    }
};

$(function () {
    $("a.ballots").click(function(e) {
        $.ajax({
            type : 'GET',
            url : '/VoteBallot',
            data : {
                "MeetingType" : this.id.substring(0, this.id.length-6),
            },

            success: function (data) {
                if (data === "0") {
                    alert("尚未上傳選票檔!");
                } else if (data === "1") {
                    alert("已開票，故無法投票!");
                } else if (data === "2") {
                    alert("已完成投票，故無法重複投票!");
                } else {
                    window.location.href = data;
                }
            },
        });
    });
});