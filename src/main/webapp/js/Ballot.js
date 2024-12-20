function sendBallot() {
    let VoteData = 0;
    let count = 0;
    $("input").each(function() {
        if($(this).is(':checked')) {
            count += 1;
            VoteData = $(this).val();
        }
    });

    console.log(VoteData);
    console.log(count);

    if (count === 1) {
        let reset = confirm("確定送出選票？");
        if (reset) {
            $.ajax({
                type: 'post',
                url: 'Vote',
                data: {
                    "VoteData": VoteData,
                },
                success: function (data) {
                    alert("投票成功!\n候選人: " + data)
                    window.location.href = "Index";
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 400) {
                        alert("已開票，故無法投票!");
                    } else if (jqXHR.status === 500) {
                        alert("已完成投票，無法重複投票!");
                    }
                    window.location.href = "Index";
                }
            });
        }
    } else {
        alert("至少須選擇一位候選人");
    }
}

function showList() {
    $.ajax({
        url : "GetCandidates",
        type : "POST",
        data: {},
        dataType : "json",
        success : function(response) {
            $(".table").append(
                "<tr class=\"row text-center\">" +
                    "<th class=\"col\">勾選：</th>" +
                    "<th class=\"col\">候選人：</th>" +
                "</tr>"
            );
            for (let i = 0; i < response.length; i++) {
                $(".table").append(
                    "<tr class=\"row\">" +
                        "<td class=\"col d-flex justify-content-center\">" +
                            "<input id=\"candidate0\" type=\"radio\" value=\""+ response[i].uuid +"\" name=\"radioBox\" class=\"ballotInput\"/>" +
                        "</td>" +
                        "<td class=\"col d-flex flex-column align-itmes-center text-center\">" +
                            "<div class=\"card-flip\">" +
                                "<div class=\"flip\">" +
                                    "<div class=\"front d-flex justify-content-center\">" +
                                        "<div class=\"card cardSize\">" +
                                            "<img src=\"img/candidateIMG/" + response[i].uuid + ".png\"/>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class=\"back d-flex justify-content-center\">" +
                                        "<div class=\"card cardSize d-flex align-items-center position-relative\"> <div class=\"text-center position-absolute card-content\">" + response[i].introduction + "</div></div>" +
                                    "</div>" +
                                "</div>" +
                            "</div>" +
                            "<br/>" + response[i].name +
                        "</td>" +
                    "</tr>"
                );
            }
        }
    });
}

function showTitle() {
    $.ajax({
        url : "GetTitle",
        type : "POST",
        data: {},
        success : function(response) {
            console.log(response);
            $("#votingActivityTitle").text("投票活動標題: " + response);
        }
    });
}

function init() {
    showTitle();
    showList();
}

$(document).ready(function(){
    init();
});
