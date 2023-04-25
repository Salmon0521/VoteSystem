function Download(filename){
    url = 'DownloadFile?Filename='+filename;
    window.open(url);
}

function showList() {
    let fileNameList = $("#fileNameList")[0].textContent.split(",");

    for (let i = 1; i < fileNameList.length; i++) {
     $(".table").append("<tr><td>"+
         "<a>"+fileNameList[i]+"</a>"+
        "<button class=\"span-right\" onclick=\"Download('"+fileNameList[i]+"')\">下載</button>"+
         "</td></tr>");
    }
}
$(document).ready(function(){
    showList();
});