$(function () {


    $("#jqGrid").jqGrid({
        url: '/api/tags/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '标签名称', name: 'name', index: 'name', width: 50},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 50}
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


    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });


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

function addTag() {
    reset();
    $('.modal-title').html('标签添加');
    $("#tagModal").modal('show');

}


function validSaveData() {
    $('#edit-error-msg').css("display", "none");
    $('#edit-error-msg').html("");

    var name = $("#name").val();
    if (isNull(name)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("标签名称不能为空");
        return false;
    }

    return true;
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {


    if (validSaveData()) {

        var param = {
            "name": $("#name").val(),
        };
        var url = '/api/tags/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/api/tags/update';
            param["id"] = id;

        }

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(param),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            success: function (result) {
                $('#tagModal').modal('hide');
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

function editTag() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    $('.modal-title').html('标签编辑');
    $('#tagModal').modal('show');
    $("#tagId").val(id);


    // 请求数据
    $.get("/api/tags/info/" + id, function (result) {
        $("#name").val(result.name);
    })
}


function deleteTag() {
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
                    url: "/api/tags/delete",
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
}