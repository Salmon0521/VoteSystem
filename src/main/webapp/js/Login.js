function login() {
    let account = $("#account").val();
    let password = $("#password").val();

    if (account != "" && password != "") {
        $.ajax({
            type: 'post',
            url: 'CheckLogin',
            data: {
                "Account": account,
                "Password": password,
            },
            success: function (data) {
                if (data === "error") {
                    alert("帳號密碼錯誤!");
                    window.location.href = "Login";
                }
                else {
                    window.location.href = "Index";
                }
            }
        });
    } else {
        alert("請填寫帳號密碼!");
    }
}