var addState = false;

function addCandidate() {
    if (addState === false) {
        addState = true;
        $("#newCandidateTable").append("<tr class=\"row\">" +
            "<td class=\"col-3\">" + "<input type=\"file\" name=\"candidateIMG\" id=\"file\" accept=\".jpg,.jpmg,.png\" />" + "</td>" +
            "<td class=\"col-3\">" + "<input type=\"text\" name=\"candidateName\" id=\"name\" required />" + "</td>" +
            "<td class=\"col-3\">" + "<input type=\"text\" name=\"candidateIntroduction\" id=\"introduction\" />" + "</td>" +
            "<td class=\"col-3\">" + "<input class=\"btn btn-secondary\" type=\"submit\" value=\"確認\" />" + "</td>" +
            "</tr>");
    } else {
        alert("請先完成新增!");
    }
}

function deleteCandidate(CandidateUUID) {
    document.getElementById(CandidateUUID).remove();
    $.ajax({
        url : "DeleteCandidate",
        type : "POST",
        data: {
            "candidateUUID" : CandidateUUID
        },
        success : function(response) {
            window.location.reload();
        }
    });
}

function createVoteActivity() {
    $.ajax({
        url : "CreateVoteActivity",
        type : "POST",
        data: {},
        success : function(response) {
            if (response === "0") {
                alert("投票活動建立成功!");
                window.location.href = "ManageVoteActivity";
            }
            else if (response === "1") {
                alert("候選人至少需要一個");
                window.location.reload();
            }
            else if (response === "2") {
                alert("已存在投票活動");
                window.location.href = "ManageVoteActivity";
            }
        }
    })
}

function showList() {
    $.ajax({
        url : "GetCandidates",
        type : "POST",
        data: {},
        dataType : "json",
        success : function(response) {
            $("#candidateTable").append("<tr  class=\"row\">" +
                "<th class=\"col-3\">候選人照片</th>" +
                "<th class=\"col-3\">候選人姓名</th>" +
                "<th class=\"col-3\">候選人介紹</th>" +
                "</tr>"
            );
            for (let i = 0; i < response.length; i++) {
                $("#candidateTable").append("<tr id=\"" + response[i].uuid + "\" class=\"row\">" +
                    "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + "<img src=\"img/candidateIMG/" + response[i].uuid + ".png\" width=\"100\" height=\"100\"/>" + "</td>" +
                    "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + response[i].name + "</td>" +
                    "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + response[i].introduction + "</td>" +
                    "<td class=\"col-3 align-items-center d-flex justify-content-center\">" + "<input class=\"btn btn-danger\" type=\"submit\" onclick=\"deleteCandidate('" + response[i].uuid + "')\" value=\"刪除\">" + "</td>" +
                    "</tr>"
                );
            }
        }
    });
}

$(document).ready(function(){
    showList();
});
