function sendBallot() {
    var VoteData = 0;
    var i = 0;
    let total = 0;
    let count = 0;
    $("input").each(function() {
        total += 1;
        if($(this).is(':checked')) {
            count += 1;
            VoteData = $(this).val();
        }
    });

    console.log(JSON.stringify(VoteData));

    if (count*2 == total) {
        let reset = confirm("確定送出選票？");
        if (reset) {
            $.ajax({
                type: 'post',
                url: 'Vote',
                data: {
                    "VoteData": VoteData,
                },
                success: function (data) {
                    window.location.href = "VoteIndex";
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    /*if (jqXHR.status === 400) {
                        alert("已完成投票，無法重複投票!");
                    } else if (jqXHR.status === 500) {
                        alert("已開票，故無法投票!");
                    }
                    window.location.href = "VoteIndex";*/
                }
            });
        }
    } else {
        alert("選票填寫錯誤!");
    }
}