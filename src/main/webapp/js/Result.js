let interval;

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

function showList() {
    $.ajax({
        url : "GetResult",
        type : "POST",
        data: {},
        dataType : "json",
        success : function(response) {
            console.log(response);
            $("#ResultTable").append("<tr  class=\"row\">" +
                "<th class=\"col-3\">候選人照片</th>" +
                "<th class=\"col-3\">候選人姓名</th>" +
                "<th class=\"col-3\">候選人得票數</th>" +
                "<th class=\"col-3\">當選</th>" +
                "</tr>"
            );
            let maxNum = response[0].count;
            for (let i = 0; i < response.length; i++) {
                if (i === 0 || response[i].count === maxNum) {
                    $("#ResultTable").append(
                        "<tr class=\"row\">" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + "<img src=\"img/candidateIMG/" + response[i].image + "\" width=\"100\" height=\"100\"/>" + "</td>" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + response[i].name + "</td>" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + response[i].count + "</td>" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + "O" + "</td>" +
                        "</tr>"
                    );
                }
                else {
                    $("#ResultTable").append(
                        "<tr class=\"row\">" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + "<img src=\"img/candidateIMG/" + response[i].image + "\" width=\"100\" height=\"100\"/>" + "</td>" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + response[i].name + "</td>" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + response[i].count + "</td>" +
                            "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + "X" + "</td>" +
                        "</tr>"
                    );
                }
            }
        }
    });
}

function countBallot() {
    interval = setInterval(function(){
        $.ajax({
            type : 'POST',
            url : 'CountBallot',
            success: function (response) {
                console.log(response);
                // document.getElementById("count").innerHTML = response;
                // $("#totalVoting").text("總投票數： " + response);

            },
        });
    }, 1500);
}

clearInterval(interval)

function init() {
    showTitle();
    showList();
    countBallot();
}

$(document).ready(function(){
    init();
});