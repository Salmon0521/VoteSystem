var addState = false;

function addCandidate() {
    if (addState === false) {
        addState = true;
        $("#newCandidateTable").append("<tr>" +
            "<td align=center>" + "<input type=\"file\" name=\"candidateIMG\" id=\"file\" accept=\".jpg,.jpmg,.png\" />" + "</td>" +
            "<td align=center>" + "<input type=\"text\" name=\"candidateName\" id=\"name\" required />" + "</td>" +
            "<td align=center>" + "<input type=\"text\" name=\"candidateIntroduction\" id=\"introduction\" />" + "</td>" +
            "<td align=center>" + "<input style=\"width:6em\" type=\"submit\" value=\"確認\" />" + "</td>" +
            "</form></tr>");
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

function showList() {
    $.ajax({
        url : "GetCandidates",
        type : "POST",
        data: {},
        dataType : "json",
        success : function(response) {
            for (let i = 0; i < response.length; i++) {
                $("#candidateTable").append("<tr id=\"" + response[i].uuid + "\">" +
                    "<td align=center>" + "<img src=\"img/candidateIMG/" + response[i].uuid + ".png\" width=\"100\" height=\"100\"/>" + "</td>" +
                    "<td align=center>" + response[i].name + "</td>" +
                    "<td align=center>" + response[i].introduction + "</td>" +
                    "<td align=center>" + "<input style=\"width:6em\" type=\"submit\" onclick=\"deleteCandidate('" + response[i].uuid + "')\" value=\"刪除\">" + "</td>" +
                    "</tr>"
                );
            }
        }
    });
}

$(document).ready(function(){
    showList();
});
