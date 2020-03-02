/**
 * 发起投票 JS
 * @author Xmchx
 * */
var mode = 0;
$(function () {
    setLayDate();
    $(".alert").hide();
    // 隐藏 选项数量
    $("#optionNum").hide();

    // 监听 投票模式选择事件
    $("#voteMode").change(function (data) {
        // 获取选中的值
        // 1 Single election
        // 2 Multiply election
        var value = $("#voteMode option:selected").attr("value");
        mode = Number(value);
        if (Number(value) == 2) {
            $("#optionNum").show();
        } else {
            $("#optionNum").hide();
        }

    });

    $("#submit").on("click", submitVote);

});

function clear() {
    $("#voteTitle").val('');
    $("#voteDescribe").val('');
    $("#voteMode").val('Choose...');
    $("#voteCategory").val('Choose...');
    $("#deadline").val('');
    $("#optionNum").hide();
    $("#modeInput").val('');
}

function submitVote() {
    clearAllError();
    setTimeout(function () {
        var title = $("#voteTitle").val();
        var summary = $("#voteDescribe").val();
        var deadline = $("#deadline").val();
        var limitCount = $("#modeInput").val();
        var voteCategoryId = $("#voteCategory").val();
        var optionData = [];
        for (var i = 1; i <= options; i++) {
            optionData[i - 1] = $("#option" + i).val();
        }
        if (checkAll()) {
            var tagsinput = $("#voteTags").tagsinput('items');
            var data = {
                "title": title,
                "summary": summary,
                "mode": mode,
                "expirationDateTime": new Date(deadline),
                "limitCount": Number(limitCount),
                "choices": optionData,
                "tags": tagsinput,
                "categoryId": voteCategoryId
            };
            $.ajax({
                url: "/api/polls/save",
                data: JSON.stringify(data),
                method: "post",
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                success: function (data) {
                    alert(data.message);
                    clear();
                },
                error: function () {
                    alert("没有权限!");
                }
            });
        } else {
            $(".alert").show();
        }

    }, 800);

}

var options = 3;

function addOption() {
    $("#close" + options).remove();
    ++options;
    var append = "" +
        "   <div class=\"option" + options + "\">\n" +
        "       <div class=\"input-group mb-3 mr-sm-3\">\n" +
        "           <div class=\"input-group-prepend\">\n" +
        "               <span class=\"input-group-text\">Option " + options + "</span>\n" +
        "           </div>\n" +
        "           <input type=\"text\" class=\"form-control" +
        " py-0 my-option\" onchange=\"checkOptionName(this);\" id=\"option" + options + "\">\n" +
        "            <div class=\"cls\"></div>\n" +
        "           <div class=\"input-group-append\" id=\"close" + options + "\">\n" +
        "               <span class=\"input-group-text\"" +
        " onclick=\"delOption()\">\n" +
        "                   <i class=\"fas fa-times\"></i>\n" +
        "               </span>\n" +
        "           </div>\n" +
        "       </div>\n" +
        "   </div>\n"
    ;
    $(".add").append(append);
}

function delOption() {
    $(".option" + options).remove();
    --options;
    $(".option" + options + " .cls").after("" +
        " <div class=\"input-group-append\" id=\"close" + options + "\">\n" +
        "   <span class=\"input-group-text\"" +
        " onclick=\"delOption()\">\n" +
        "       <i class=\"fas fa-times\"></i>\n" +
        "   </span>\n" +
        " </div>\n"
    );
}

// All input check
function checkAll() {
    var allDone = true;

    // Options check
    for (var i = 1; i <= options; i++) {
        var selectionText = $("#option" + i).val();
        if (selectionText == "") {
            allDone = false;
            $("#option" + i).addClass("is-invalid");
        }
    }
    // Build params
    params = {};
    params.title = $("#voteTitle").val();
    params.describe = $("#voteDescribe").val();
    params.deadline = $("#deadline").val();
    params.mode = $("#voteMode").val();
    params.limit = $("#modeInput").val();
    params.category = $("#voteCategory").val();
    // Verify
    if (params.title == "") {
        allDone = false;
        $("#voteTitle").addClass("is-invalid");
    }
    if (params.describe == "") {
        allDone = false;
        $("#voteDescribe").addClass("is-invalid");
    }
    if (params.deadline == "") {
        allDone = false;
        alert("Please, Select Deadline!")
    }


    // Limit
    if (!isNaN(Number(params.mode))) {

        if (mode == 2 && Number($("#modeInput").val()) > options) {
            allDone = false;
            $("#voteMode").addClass("is-invalid");
        }
    } else {
        allDone = false;
        $("#voteMode").addClass("is-invalid");
    }

    if (mode == 2) { // Multiply election
        var num = $("#modeInput").val();
        if (Number(num) <= 0 && Number(num) != -1) {
            allDone = false;
            $("#modeInput").addClass("is-invalid");
        }
    }


    // vote Category
    if (isNaN(Number(params.category))) {
        allDone = false;
        $("#voteCategory").addClass("is-invalid");
    }

    return allDone;

}

/**检测选项名称不能相同*/
function checkOptionName(data) {
    var values = "";
    $(".my-option").each(function (i, item) {
        var value = $(this).val();
        values += value; // 获取所有选项值
    })
    var val = $(data).val();
    var newValue = values.replace(val, ""); // 去除当前的值
    if (newValue == "") {
        return false;
    } else {
        if (newValue.indexOf(val) > -1) {
            alert("选项名称已存在，请重新输入!");
            $(data).val("");
        }
    }
}

function clearAllError() {
    for (var i = 1; i <= options; i++) {
        $("#option" + i).removeClass("is-invalid");
    }
    $("#voteCategory").removeClass("is-invalid");
    $("#voteMode").removeClass("is-invalid");
    $("#voteDescribe").removeClass("is-invalid");
    $("#voteTitle").removeClass("is-invalid");
    $("#modeInput").removeClass("is-invalid");
}

// 设置日期控件
function setLayDate() {
    /* laydate */
    /* 对截止日期设置范围 7天内  截止时间不少于2小时*/
    var curDate = new Date();
    var futureDate = new Date();
    futureDate.setDate(curDate.getDate() + 7);
    // 拼接
    curDate.setHours(curDate.getHours() + 2);
    var cdate = curDate.getFullYear() + '-' + (curDate.getMonth() + 1) + '-' + curDate.getDate();
    var fdate =
        futureDate.getFullYear() + '-' + (futureDate.getMonth() + 1) + '-' + futureDate.getDate();
    cdate = cdate + ' ' + curDate.toLocaleTimeString().slice(2);
    fdate = fdate + ' ' + futureDate.toLocaleTimeString().slice(2);
    laydate.render({
        elem: "#deadline"
        , type: 'datetime'
        , btns: ['clear', 'confirm']
        , min: cdate
        , max: fdate
        , lang: 'en'
        , trigger: 'click'
    });

}