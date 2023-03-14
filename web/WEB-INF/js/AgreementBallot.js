window.history.pushState(null, document.title, location.href);
window.addEventListener("popstate", e=> {
    window.history.pushState(null, document.title, location.href);
    window.location.replace('/VoteIndex');
});

function sendBallot() {
    var VoteData = {};
    var i = 0;
    let total = 0;
    let count = 0;
    $("input").each(function() {
        total += 1;
        if($(this).is(':checked')) {
            count += 1;
            let inputDict = {};
            if ($(this).val().trim() == 'agree') {
                inputDict['col1'] = $(this).parent().parent().prev().prev().text().trim();
                inputDict['col2'] = $(this).parent().parent().prev().text().trim();
                inputDict['agreement'] = $(this).val().trim();
            }
            else {
                inputDict['col1'] = $(this).parent().parent().prev().prev().prev().text().trim();
                inputDict['col2'] = $(this).parent().parent().prev().prev().text().trim();
                inputDict['agreement'] = $(this).val().trim();
            }
            VoteData[i] = inputDict;
            i+=1;
        }
    });
    console.log(JSON.stringify(VoteData));

    if (count*2 === total) {
        let reset = confirm("確定送出選票？");
        if (reset) {
            $.ajax({
                type: 'post',
                url: '/Vote',
                data: {
                    "VoteData": JSON.stringify(VoteData),
                    "MeetingType": "Agreement"
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
        alert("選票填寫錯誤!");
    }
}