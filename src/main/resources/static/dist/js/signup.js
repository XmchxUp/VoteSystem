$(function () {
    $(".alert").hide();
    $("#registerBtn").on("click", function () {
        var param = {
            "name": $("#inputFullName").val(),
            "username": $("#inputUsername").val(),
            "email": $("#inputEmail").val(),
            "password": $("#inputPassword").val()
        };
        console.log(param);

        $.ajax({
            url: "/api/auth/signup",
            method: 'post',
            data: JSON.stringify(param),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            success: function (data) {
                window.location.href = "/login";
            },
            error: function (error) {
                $(".alert").show();
                $("#errorName").text(error.responseJSON.message);
            }
        });
    })
});