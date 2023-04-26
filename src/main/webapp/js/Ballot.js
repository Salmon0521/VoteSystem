function sendBallot() {
    let VoteData = 0;
    let total = 0;
    let count = 0;
    $("input").each(function() {
        total += 1;
        if($(this).is(':checked')) {
            count += 1;
            VoteData = $(this).val();
        }
    });

    console.log(VoteData);

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
                    alert("投票成功!");
                    window.location.href = "Index";
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 400) {
                        alert("已完成投票，無法重複投票!");
                    } else if (jqXHR.status === 500) {
                        alert("已開票，故無法投票!");
                    }
                    window.location.href = "Index";
                }
            });
        }
    } else {
        alert("選票填寫錯誤!");
    }
}