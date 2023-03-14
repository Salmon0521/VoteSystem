window.history.pushState(null, document.title, location.href);
window.addEventListener("popstate", e=> {
    window.history.pushState(null, document.title, location.href);
    window.location.replace('/VoteIndex');
});

function sendBallot() {
    var VoteData = {};
    var i = 0;
    $("input").each(function() {
        let inputDict = {};
        inputDict['col1'] = $(this).parent().prev().prev().text().trim();
        inputDict['col2'] = $(this).parent().prev().text().trim();
        inputDict['score'] = $(this).val().trim();
        VoteData[i] = inputDict;
        i+=1;
    });
    console.log(JSON.stringify(VoteData));

    if (checkRank()) {
        let vote = confirm("確定送出選票？");
        if (vote) {
            $.ajax({
                type: 'post',
                url: '/Vote',
                data: {
                    "VoteData": JSON.stringify(VoteData),
                    "MeetingType": "Rank"
                },

                success: function (data) {
                    window.location.href = "/VoteIndex";
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 400) {
                        alert("已開票，故無法投票!");
                    } else if (jqXHR.status === 450) {
                        alert("已完成投票，無法重複投票!");
                    } else if (jqXHR.status === 500) {
                        alert("投票已重置，故無法投票!");
                    }
                    window.location.href = "/VoteIndex";
                }
            });
        }
    } else {
        alert("序位填寫錯誤!");
    }
}

function checkRank() {
    let rankInput = [];
    $("input").each(function() {
        rankInput.push($(this).val().trim());
    });

    for (let i=1; i<=rankInput.length; i++) {
        if (!rankInput.includes(i.toString())) {
            return false;
        }
    }
    return true;
}