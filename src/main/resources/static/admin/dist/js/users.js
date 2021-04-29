$(function () {


    $("#jqGrid").jqGrid({
        url: '/api/users/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '用户名', name: 'username', index: 'username', width: 40},
            {label: '名称', name: 'name', index: 'name', width: 40},
            {label: '邮箱', name: 'email', index: 'email', width: 60},
            {
                label: '头像预览图',
                name: 'avatar',
                index: 'avatar',
                width: 60,
                formatter: coverImageFormatter
            },
            {label: '权限', name: 'roles', index: 'roleName', width: 60, formatter: setRoleName},
            {label: '状态', name: 'status', index: 'status', width: 60, formatter: statusFormatter},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 90}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "content",
            page: "page",
            total: "totalPages",
            records: "totalElements"
        },
        prmNames: {
            page: "page",
            rows: "size",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });


    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"40\" width=\"40\" alt='avatar'/>";
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function setRoleName(cellvalue) {
        var roleName = "";
        var jsonLen = getJsonLength(cellvalue);
        for (let i = 0; i < jsonLen; i++) {
            if (i != jsonLen - 1) {
                roleName += cellvalue[i].name + "-";
            } else {
                roleName += cellvalue[i].name
            }
        }
        return roleName;
    }

    function statusFormatter(cellvalue) {
        if (cellvalue == "locked") {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\"" +
                " style=\"width: 50%;\">封杀</button>";
        } else {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\"" +
                " style=\"width: 50%;\">活动</button>";
        }
    }


    jQuery("select.image-picker").imagepicker({
        hide_select: false,
    });

    jQuery("select.image-picker.show-labels").imagepicker({
        hide_select: false,
        show_label: true,
    });

    var container = jQuery("select.image-picker.masonry").next("ul.thumbnails");
    container.imagesLoaded(function () {
        container.masonry({
            itemSelector: "li",
        });
    });
});

/**
 * 搜索功能
 */
function search() {
    var keyword = $('#keyword').val();
    if (!validLength(keyword, 20)) {
        swal("搜索字段长度过大！", {
            icon: "error",
        });
        return false;
    }

    var searchData = {keyword: keyword};
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/api/users/search'}).trigger("reloadGrid");
}

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function addUser() {
    reset();
    $('.modal-title').html('用户添加');
    $("#userModal").modal('show');

}


function validSaveData() {
    $('#edit-error-msg').css("display", "none");
    $('#edit-error-msg').html("");
    var username = $("#username").val();
    if (!validUserName(username)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的用户账号名(4-15)！");
        return false;
    }

    var name = $("#name").val();
    if (isNull(name)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("用户名称不能为空");
        return false;
    }

    var password = $("#password").val();
    if (!validPassword(password)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("用户密码验证 最少6位，最多100位字母或数字、特殊字符的组合！");
        return false;
    }

    var email = $("#email").val();
    if (!validEmail(email)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的邮箱");
        return false;
    }


    return true;
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {


    var roleIds = [];

    if (validSaveData()) {
        $('.roles-checkbox').each(function () {
            if ($(this).prop('checked') == true) {
                roleIds.push($(this).val());
            }
        });

        var param = {
            "name": $("#name").val(),
            "username": $("#username").val(),
            "email": $("#email").val(),
            "password": $("#password").val(),
            "status": $("#status").val(),
            "avatar": $("#avatar").val(),
            "roleIds": roleIds,
        };
        var url = '/api/users/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/api/users/update';
            param["id"] = id;
        }

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(param),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            success: function (result) {
                $('#userModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            },
            error: function (error) {
                swal(error.responseJSON.message, {
                    icon: "error",
                });
            }
        })
    }


});

function editUser() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    $('.modal-title').html('用户编辑');
    $('#userModal').modal('show');
    $("#userId").val(id);
    $("#username").attr("disabled", "disabled");
    $("#email").attr("disabled", "disabled");

    // 请求数据
    $.get("/api/users/info/" + id, function (result) {
        console.log(result);
        $("#username").val(result.username);
        $("#name").val(result.name);
        $("#email").val(result.email);
        $("#status").val(result.status);
        $("#avatar").val(result.avatar);

        var len = getJsonLength(result.roles);
        for (let i = 0; i < len; i++) {
            $("#role" + result.roles[i].id).prop("checked", true);
        }
    })
}


function deleteUser() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }

    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/users/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        swal("删除成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    },
                    error: function (error) {
                        swal(error.responseJSON.message, {
                            icon: "error",
                        });
                    }
                });
            }
        }
    );
}


function reset() {
    $('#edit-error-msg').css("display", "none");
    $('#edit-error-msg').html("");
    $("#name").val('');
    $("#username").val('');

    $("#email").val('');
    $("#password").val('');

    $('.roles-checkbox').each(function () {
        $(this).prop("checked", false);
    });

    $("#status option:first").prop("selected", 'selected');
    $("#avatar option:first").prop("selected", 'selected');
}