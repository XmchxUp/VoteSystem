$(function () {
    $("#saveBtn").click(function () {
        var param = {
            "name": $("#name").val(),
            "username": $("#username").val(),
            "avatar": $("#avatar").val(),
            'motto': $("#motto").val(),
        };
        let isNewName = false;
        if ($("#username").val() != $("#oldUsername").val()) {
            isNewName = true;
        }
        if (validSaveData()) {
            $.ajax({
                type: 'POST',
                url: '/users/update',
                data: JSON.stringify(param),
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                success: function (result) {
                    alert(result.message);
                    if (isNewName) {
                        window.location.href = "/logout";
                    } else {
                        window.location.href = "/index";
                    }

                },
                error: function (error) {
                    alert(error.responseJSON.message);
                }
            })
        }

    });
});


function validSaveData() {
    $('#error-msg').css("display", "none");
    $('#error-msg').html("");
    var username = $("#username").val();
    if (!validUserName(username)) {
        $('#error-msg').css("display", "block");
        $('#error-msg').html("请输入符合规范的用户账号名(4-15)！");
        return false;
    }

    var name = $("#name").val();
    if (!validCN_ENString2_40(name)) {
        $('#error-msg').css("display", "block");
        $('#error-msg').html("用户名称(2-40)");
        return false;
    }


    var avatar = $("#avatar").val();
    if (!isURL(avatar)) {
        $('#error-msg').css("display", "block");
        $('#error-msg').html("请输入符合规范的URL");
        return false;
    }

    var motto = $("#motto").val();
    if (!validCN_ENString2_40(motto)) {
        $('#error-msg').css("display", "block");
        $('#error-msg').html("座右铭（2-40）");
        return false;
    }

    return true;
}

function validUserName(userName) {
    var pattern = /^[a-zA-Z0-9_-]{4,15}$/;
    if (pattern.test(userName.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 正则匹配2-40位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_40(str) {
    var pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,40}$/;
    if (pattern.test(str.trim())) {
        return (true);
    } else {
        return (false);
    }
}

function isURL(str_url) {
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}"
        + "|"
        + "([0-9a-zA-Z_!~*'()-]+\.)*"
        + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\."
        + "[a-zA-Z]{2,6})"
        + "(:[0-9]{1,4})?"
        + "((/?)|"
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    var re = new RegExp(strRegex);
    if (re.test(str_url)) {
        return (true);
    } else {
        return (false);
    }
}