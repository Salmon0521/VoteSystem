var candidateNum = 2;
var addState = false;
function addCandidate() {
    if (addState === false) {
        addState = true;
        $("#candidateTable").append("<tr" + "id=\"" + candidateNum + "\">" +
            "<td align=center>" + "<input type=\"text\" id=\"candidateName" + candidateNum + "\"/></td>" +
            "<td align=center>" + "<input type=\"file\" name=\"candidateIMG" + candidateNum + "\"/></td>" +
            "<td align=center>" + "<input type=\"text\" id=\"candidateIntroduction" + candidateNum + "\"/></td>" +
            "<td align=center>" + "<input style=\"width:6em\" type=\"submit\" onclick=\"listCandidate()\" value=\"確認\">" + "</td>" +
            "</tr>");
    } else {
        alert("請先完成新增!");
    }
}
function listCandidate() {

}
function deleteCandidate(divIndex) {
    document.getElementById(divIndex).remove();
}
function showList() {
    $.ajax({
        url : "GetCandidates",
        type : "POST",
        data: {},
        success : function(response) {
            console.log(response);}
    //         if(response.length > 0){
    //             $("#candidateTable").empty();
    //             $("#candidateTable").append("<tr>"+
    //                 "<th>候選人姓名</th>"+
    //                 "<th>照片</th>"+
    //                 "<th>候選人簡介</th>"+
    //             for(var i=0 ; i < response.length ; i++){
    //                 $("#candidateTable").append("<tr>"+
    //                     "<td align=center>" + ifUndefine(response[i].teacher_code) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].teacher_name) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].position) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].div_calias) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].note_or_idno) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].basic_hr) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].teaching_hr) + "</td>"+
    //                     /*"<td align=center>" + ifUndefine(response[i].parttime_hr) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].substitute_hr) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].tutorial_hr) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].other) + "</td>"+*/
    //                     "<td align=center>" + ifUndefine(response[i].personal_code) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].teacher_group) + "</td>"+
    //                     "<td align=center>" + ifUndefine(response[i].semester_code) + "</td>"+
    //                     "</tr>");
    //
    //             }
    //
    //         }
    //         else{
    //             $("#addbtn").remove();
    //             $("#updatebtn").remove();
    //             $("#selectbtn").remove();
    //             //$("#importbtn").remove();
    //             $("#cancelbtn").remove();
    //             $("#delbtn").remove();
    //             $("#addconfirmbtn").remove();
    //
    //             $("#staffcode").empty();
    //             $("#staffcode").append("<tr>"+
    //                 "<th>代碼</th>"+
    //                 "<th>教師名稱</th>"+
    //                 "<th>職務</th>"+
    //                 "<th>領域</th>"+
    //                 "<th>身分證字號</th>"+
    //                 "<th>基本</th>"+
    //                 "<th>授課</th>"+
    //                 /*"<th>兼課</th>"+
    //                 "<th>代課</th>"+
    //                 "<th>輔導</th>"+
    //                 "<th>其他</th>"+*/
    //                 "<th>人事編號</th>"+
    //                 "<th>教師組別</th>"+
    //                 "<th>學期編號</th>"+
    //                 "</tr>");
    //             $("#staffcode").append("<tr class=text-center> <td colspan=10 id=empty>查無資料</td></tr>")
    //             alert('查詢無此教師');
    //         }
    // for (let i = 1; i < fileNameList.length; i++) {
    //     $("#candidateTable").append("<tr><td>"+
    //         "<a>"+fileNameList[i]+"</a>"+
    //         "<button class=\"span-right\" onclick=\"Download('"+fileNameList[i]+"')\">下載</button>"+
    //         "</td></tr>");
    });
}
$(document).ready(function(){
    showList();
});
