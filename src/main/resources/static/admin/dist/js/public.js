<!-- 正则验证 start-->
/**
 * 判空
 *
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    if (obj == null || obj == undefined || obj.trim() == "") {
        return true;
    }
    return false;
}

/**
 * 参数长度验证
 *
 * @param obj
 * @param length
 * @returns {boolean}
 */
function validLength(obj, length) {
    if (obj.trim().length < length) {
        return true;
    }
    return false;
}

/**
 * url验证
 *
 * @param str
 * @returns {boolean}
 */
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

/**
 * 用户名称验证 4到15位（字母，数字，下划线，减号）
 *
 * @param userName
 * @returns {boolean}
 */
function validUserName(userName) {
    var pattern = /^[a-zA-Z0-9_-]{4,15}$/;
    if (pattern.test(userName.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 正则匹配2-18位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_18(str) {
    var pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,18}$/;
    if (pattern.test(str.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 正则匹配2-100位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_100(str) {
    var pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,100}$/;
    if (pattern.test(str.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 用户密码验证 最少6位，最多100位字母或数字、特殊字符的组合
 *
 * @param password
 * @returns {boolean}
 */
function validPassword(password) {
    var pattern = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$)^.{6,20}$/;
    if (pattern.test(password.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 邮箱判断
 * */
function validEmail(email) {
    var pattern = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
    if (pattern.test(email.trim())) {
        return true;
    } else {
        return false;
    }
}

<!-- 正则验证 end-->

/**
 * 获取jqGrid选中的一条记录
 * @returns {*}
 */
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        swal("只能选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return selectedIDs[0];
}

/**
 * 获取Json数据的长度
 * */
function getJsonLength(jsonData) {
    var jsonLength = 0;
    for (var item in jsonData) {
        jsonLength++;
    }
    return jsonLength;
}


/**
 * 获取jqGrid选中的一条记录(不出现弹框)
 * @returns {*}
 */
function getSelectedRowWithoutAlert() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        return;
    }
    return selectedIDs[0];
}

/**
 * 获取jqGrid选中的多条记录
 * @returns {*}
 */
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return grid.getGridParam("selarrrow");
}