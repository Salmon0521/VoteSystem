let interval;

$(document).ready(function(){
    interval = setInterval(function(){
        $.ajax({
            type : 'POST',
            url : 'CountBallot',
            success: function (count) {
                document.getElementById("count").innerHTML = count;
            },
        });
    }, 2000);
})

clearInterval(interval)

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
    $.ajax({
        type: "POST",
        url: "Invoicing",
        data : {},
        success: function (data) {
            window.location.href="Index";
        },
    });
}
