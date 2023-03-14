window.history.pushState(null, document.title, location.href);
window.addEventListener("popstate", e=> {
    window.history.pushState(null, document.title, location.href);
    window.location.replace('/VoteIndex');
});

function sendBallot() {
    if (validate()) {
        let vote = confirm("確定送出選票？");
        if (vote) {
            $.ajax({
                type: 'post',
                url: '/Vote',
                data: {
                    MeetingType: "Assessment",
                    writingScore: $('#writingScore').text(),
                    researchScore: Number((parseFloat($('#researchScore').val())).toFixed(2)).toString(),
                    educationScore: Number((parseFloat($('#educationScore').val())).toFixed(2)).toString(),
                    serviceScore: Number((parseFloat($('#serviceScore').val())).toFixed(2)).toString(),
                    checkbox0: $('#checkbox0').prop("checked"),
                    checkbox1: $('#checkbox1').prop("checked"),
                    checkbox2: $('#checkbox2').prop("checked"),
                    checkbox3: $('#checkbox3').prop("checked"),
                    checkbox4: $('#checkbox4').prop("checked"),
                    checkbox5: $('#checkbox5').prop("checked"),
                    checkbox6: $('#checkbox6').prop("checked"),
                    checkbox7: $('#checkbox7').prop("checked"),
                    checkbox8: $('#checkbox8').prop("checked"),
                    checkbox9: $('#checkbox9').prop("checked"),
                    checkbox10: $('#checkbox10').prop("checked"),
                    otherReason: $('#otherReason').val(),
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
    }
}

function validate(){
    if (!$("#researchScore").val()) {
        alert("尚未填寫【B.研究】評分!");
    } else if (!$("#educationScore").val()) {
        alert("尚未填寫【C.教學】評分!");
    } else if (!$("#serviceScore").val()) {
        alert("尚未填寫【D.服務】評分!");
    } else if (parseFloat($("#researchScore").val()) < parseFloat($("#researchScore").attr('min')) || parseFloat($("#researchScore").val()) > parseFloat($("#researchScore").attr('max'))) {
        alert("【B.研究】填寫評分超出範圍!");
    } else if (parseFloat($("#educationScore").val()) < parseFloat($("#educationScore").attr('min')) || parseFloat($("#educationScore").val()) > parseFloat($("#educationScore").attr('max'))) {
        alert("【C.教學】填寫評分超出範圍!");
    } else if (parseFloat($("#serviceScore").val()) < parseFloat($("#serviceScore").attr('min')) || parseFloat($("#serviceScore").val()) > parseFloat($("#serviceScore").attr('max'))) {
        alert("【D.服務】填寫評分超出範圍!");
    } else if ($("#checkbox10").prop("checked") && !$("#otherReason").val()) {
        alert("尚未填寫其他理由!");
        return false;
    } else if ($("#checkboxRow17")[0].style.display === "table-row" &&
                (!($("#checkbox0").prop("checked") || $("#checkbox1").prop("checked") || $("#checkbox2").prop("checked") || $("#checkbox3").prop("checked")
                || $("#checkbox4").prop("checked") || $("#checkbox5").prop("checked") || $("#checkbox6").prop("checked") || $("#checkbox7").prop("checked")
                || $("#checkbox8").prop("checked") || $("#checkbox9").prop("checked") || $("#checkbox10").prop("checked")))) {
        alert("總分未通過需至少勾選一項升等未過理由!");
        return false;
    } else {
        return true;
    }
}

function computeTotalScore() {
    var title = $("td.X2").text();
    if (!title.includes("兼任")) {
        var totalScore = parseFloat($("td.X21").text());
    } else {
        var totalScore = parseFloat($("td.X20").text());
    }

    if ($("#researchScore").val()) {
        if (parseFloat($("#researchScore").val()) < parseFloat($("#researchScore").attr('min')) || parseFloat($("#researchScore").val()) > parseFloat($("#researchScore").attr('max'))) {
            alert("【B.研究】填寫評分超出範圍!");
        }
        totalScore += parseFloat($("#researchScore").val());
    }
    if ($("#educationScore").val()) {
        if (parseFloat($("#educationScore").val()) < parseFloat($("#educationScore").attr('min')) || parseFloat($("#educationScore").val()) > parseFloat($("#educationScore").attr('max'))) {
            alert("【C.教學】填寫評分超出範圍!");
        }
        totalScore += parseFloat($("#educationScore").val());
    }
    if ($("#serviceScore").val()) {
        if (parseFloat($("#serviceScore").val()) < parseFloat($("#serviceScore").attr('min')) || parseFloat($("#serviceScore").val()) > parseFloat($("#serviceScore").attr('max'))) {
            alert("【D.服務】填寫評分超出範圍!");
        }
        totalScore += parseFloat($("#serviceScore").val());
    }

    var totalId;
    if (!title.includes("兼任")) {
        totalId = "td.X18";

    } else {
        totalId = "td.X17";
    }

    totalScore = Number((totalScore).toFixed(2));
    $(totalId).text(totalScore);
    var threshold;
    var displayType;
    if (!title.includes("副")) {
        threshold = 80;
    } else {
        threshold = 78;
    }
    if (totalScore >= threshold) {
        $(totalId)[0].style.color = "blue";
        displayType = "none";

        $('#otherReason').val("");
        for (let j = 0; j <= 10; j++) {
            let checkboxId = "#checkbox" + j.toString();
            $(checkboxId).prop("checked", false);
        }
    } else {
        $(totalId)[0].style.color = "red";
        if ($("#researchScore").val() && $("#educationScore").val() && $("#serviceScore").val()) {
            displayType = "table-row";
        } else {
            displayType = "none";
        }
    }

    for (let i = 17; i <= 21; i++) {
        $("#checkboxRow"+i)[0].style.display = displayType;
    }
}

function checkOtherReason() {
    if (!$('#checkbox10').prop("checked")) {
        $('#otherReason').val("");
    }
}