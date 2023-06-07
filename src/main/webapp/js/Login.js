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
            success: function (response) {
                console.log(response.status);
                if (response === "1") {
                    alert("帳號密碼錯誤!");
                    window.location.href = "Login";
                }
                else if (response === "2"){
                    alert("帳號為無效帳號");
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