$(function () {


    $("#jqGrid").jqGrid({
        url: '/api/polls/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 40, key: true, hidden: true},
            {label: '标题', name: 'title', index: 'title', width: 70},
            {label: '摘要', name: 'summary', index: 'summary', width: 70},
            {label: '模式', name: 'mode', index: 'mode', width: 30, formatter: setModeName},
            {label: '多选个数', name: 'limitCount', index: 'limitCount', width: 40},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 80},
            {label: '截止时间', name: 'expirationDateTime', index: 'expirationDateTime', width: 80},
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

    function setModeName(cellvalue) {
        var modeName = "";
        var jsonLen = getJsonLength(cellvalue);
        if (cellvalue == 1) {
            modeName = "单选";
        } else {
            modeName = "多选";
        }
        return modeName;
    }


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
    $("#jqGrid").jqGrid("setGridParam", {url: '/api/polls/search'}).trigger("reloadGrid");
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

function addPoll() {
    window.location.href = "/admin/polls/add";
}

function editPoll() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/polls/edit/" + id;
}


function deletePoll() {
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
                    url: "/api/polls/delete",
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


