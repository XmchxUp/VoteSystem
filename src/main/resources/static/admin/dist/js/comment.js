$(function () {
    $("#jqGrid").jqGrid({
        url: '/api/comments/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '评论内容', name: 'content', index: 'content', width: 120},
            {label: '评论时间', name: 'createTime', index: 'createTime', width: 60},
            {
                label: '头像预览图',
                name: 'avatar',
                index: 'avatar',
                width: 60,
                formatter: coverImageFormatter
            },
            {label: '评论人名称', name: 'commentator', index: 'commentator', width: 60},
            {label: '评论人邮箱', name: 'email', index: 'email', width: 90},

            {label: '状态', name: 'commentStatus', index: 'commentStatus', width: 60, formatter: statusFormatter},
            {label: '回复内容', name: 'replyBody', index: 'replyBody', width: 120},
        ],
        height: 700,
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
    function statusFormatter(cellvalue) {
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 80%;\">待审核</button>";
        }
        else if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 80%;\">已审核</button>";
        }
    }

});

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 批量审核
 */
function checkDoneComments() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认审核通过吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/comments/checkDone",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        swal("审核成功", {
                            icon: "success",
                        });
                        reload();
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

/**
 * 批量删除
 */
function deleteComments() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认删除这些评论吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/comments/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        swal("删除成功", {
                            icon: "success",
                        });
                        reload();
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


function reply() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid('getRowData', id);
    console.log(rowData);
    if (rowData.commentStatus.indexOf('待审核') > -1) {
        swal("请先审核该评论再进行回复!", {
            icon: "warning",
        });
        return;
    }
    $("#replyBody").val('');
    $('#replyModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var replyBody = $("#replyBody").val();
    if (!validCN_ENString2_100(replyBody)) {
        swal("请输入符合规范的回复信息!", {
            icon: "warning",
        });
        return;
    } else {
        var url = '/admin/comments/reply';
        var id = getSelectedRow();
        var params = {"commentId": id, "replyBody": replyBody};
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                swal("回复成功", {
                    icon: "success",
                });
                reload();
            },
            error: function (error) {
                swal(error.responseJSON.message, {
                    icon: "error",
                });
            }
        });
    }
});
