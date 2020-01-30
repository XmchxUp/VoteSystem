$(function () {
    setInterval(setCountDown, 1000);
    /*setInterval(setJoin,60000*60);*/
    setJoin();
});

/*Vote按钮*/
function voteSubmit(element) {


    // 判断是否截止
    var now = new Date();
    var voteId = $(element).attr("voteId");
    var deadlineTimeStamp = $("span[data-voteid=" + voteId + "]").attr("data-deadline");
    var deadline = new Date(deadlineTimeStamp);
    if (element.innerHTML == "over") {
        if ($(element).attr("isOverDue") == "true") {
            alert("已经结束！");
            return;
        } else {
            if (moment(now).isSameOrAfter(deadline)) {
                $(element).attr("isOverDue", "true");
                alert("已经结束！");
                return;
            }
        }
    }

    var voteId = parseInt(element.getAttribute("voteId"));


    var optionIds = new Array();
    $("input[name=" + voteId + "]:checked").each(function () {
        optionIds.push(parseInt($(this).attr("id")));
    });
    // 判断是否选项超出
    var limitCount = Number($(element).attr("limitCount"));
    console.log(limitCount);
    if (limitCount != 0 && limitCount != -1) {
        if (optionIds.length > limitCount) {
            alert("该投票最多选" + limitCount + "个");
            return;
        }
    }

    // 投票请求
    $.post("/votes", {
        voteId: voteId,
        optionIds: optionIds
    }).done(function (data) {
        if (data.message != null) {
            alert(data.message);
        } else {
            alert("请登录!");
        }

        window.location.reload();
    }).fail(function (error) {
        alert("请求失败！");
    });

}

function setCountDown() {
    // 获取所有countDown元素的 投票ID 和 截止时间
    // 并进行设置
    $(".deadline").each(function () {
        setCountTime($(this).attr("data-deadline"), this);
    });
}

function setJoin() {
    // 设置是否参与过
    $(".deadline").each(function () {
        setVote($(this).attr("data-voteId"));
    });
}

function setVote(id) {
    $.get("/isVote", {
        voteId: id
    }).done(function (data) {
        if (data.message == "true") {
            // 参与过了
            $("#voteButton" + id).hide();
            $("#voteInfo" + id).show();
        }
    })
}

/*当前时间与截止差 并设置所剩时间*/
function setCountTime(deadlineTimeStamp, element) {
    /*进行时间结束判断 并提交POST 并判断过期是否2小时 设置过期属性*/
    var now = new Date();
    var deadline = new Date(deadlineTimeStamp);
    if (element.innerHTML == "over") {
        if ($(element).attr("isOverDue") == "true") {
            return;
        } else {
            if (moment(now).isSameOrAfter(deadline)) {
                $(element).attr("isOverDue", "true");
            }
        }
    }

    var now_time = new Date().getTime();
    var deadline_time = new Date(deadline).getTime();
    // 设置时间样式 天 小时 分 秒
    var time_diff = deadline_time - now_time;
    // 天
    var days = Math.floor(time_diff / (24 * 3600 * 1000));
    if (days > 0) {
        element.innerHTML = days + " days left";
        return;
    }
    // 小时
    var hours = Math.floor(time_diff / (3600 * 1000));
    if (hours > 0) {
        element.innerHTML = hours + " hours left";
        return;
    }

    // 分
    var minutes = Math.floor(time_diff / (60 * 1000));
    if (minutes > 0) {
        element.innerHTML = minutes + " minutes left";
        return;
    }

    // 秒
    var seconds = Math.floor(time_diff / 1000);
    if (seconds > 0) {
        element.innerHTML = seconds + " seconds left";
        return;
    }

    element.innerHTML = "over";
    var id = element.getAttribute("data-voteId");
    $("#voteInfo" + id).show();
    $("#voteButton" + $(element).attr("data-voteid")).remove();
}