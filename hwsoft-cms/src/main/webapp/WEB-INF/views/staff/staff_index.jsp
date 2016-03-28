<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
<head>
    <b:base></b:base>
    <title>H+ 后台主题UI框架 - Bootstrap Table</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="favicon.ico"> <link href="theme/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="theme/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="theme/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="theme/css/animate.min.css" rel="stylesheet">
    <link href="theme/css/style.min862f.css?v=4.1.0" rel="stylesheet">

    <link href="theme/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="theme/js/plugins/layer/skin/layer.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <div class="ibox-tools">
                <a class="collapse-link">
                    <i class="fa fa-chevron-up"></i>
                </a>
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-wrench"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="#">选项1</a>
                    </li>
                    <li><a href="#">选项2</a>
                    </li>
                </ul>
                <a class="close-link">
                    <i class="fa fa-times"></i>
                </a>
            </div>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <div class="example">
                            <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                <button type="button" class="btn btn-outline btn-default" id = "add">
                                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                </button>
                                <button type="button" class="btn btn-outline btn-default"  id="update">
                                    <i class="glyphicon glyphicon-heart" aria-hidden="true"></i>
                                </button>
                                <button type="button" class="btn btn-outline btn-default" id ="delete">
                                    <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                </button>
                            </div>
                            <table id="exampleTableEvents" data-height="400" data-mobile-responsive="true">
                                <thead>
                                <tr>
                                    <th data-field="state" data-checkbox="true"></th>
                                    <th data-field="id">ID</th>
                                    <th data-field="name">名称</th>
                                    <th data-field="realname">姓名</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
    </div>
    <!-- End Panel Other -->
</div>
<script src="theme/js/jquery.min.js?v=2.1.4"></script>
<script src="theme/js/bootstrap.min.js?v=3.3.6"></script>
<script src="theme/js/content.min.js?v=1.0.0"></script>
<script src="theme/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="theme/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="theme/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<script src="theme/js/plugins/layer/layer.min.js"></script>
<script src="theme/js/plugins/sweetalert/sweetalert.min.js"></script>

<script type="text/javascript" charset="UTF-8">
    $("#exampleTableEvents").bootstrapTable({
        url:"staff/list.json",
        search:!0,
        pagination:!0,
        showRefresh:!0,
        showToggle:!0,
        showColumns:!0,
        iconSize:"outline",
        sidePagination: "server",
        toolbar:"#exampleTableEventsToolbar",
        icons:{refresh:"glyphicon-repeat",toggle:"glyphicon-list-alt",columns:"glyphicon-list"}
    });
    var e=$("#examplebtTableEventsResult");
    $("#exampleTableEvents")
            .on("all.bs.table",function(e,t,o){console.log("Event:",t,", data:",o)})
            .on("click-row.bs.table",function(){

                var ids = $("#exampleTableFromData").bootstrapTable("getSelections");

                e.text(ids)
            })
            .on("dbl-click-row.bs.table",function(){e.text("Event: dbl-click-row.bs.table")})
            .on("sort.bs.table",function(){e.text("Event: nihao")})
            .on("check.bs.table",function(){e.text("Event: check.bs.table")})
            .on("uncheck.bs.table",function(){e.text("Event: uncheck.bs.table")})
            .on("check-all.bs.table",function(){e.text("Event: check-all.bs.table")})
            .on("uncheck-all.bs.table",function(){e.text("Event: uncheck-all.bs.table")})
            .on("load-success.bs.table",function(){e.text("Event: load-success.bs.table")})
            .on("load-error.bs.table",function(){e.text("Event: load-error.bs.table")})
            .on("column-switch.bs.table",function(){e.text("Event: column-switch.bs.table")})
            .on("page-change.bs.table",function(){e.text("Event: page-change.bs.table")})
            .on("search.bs.table",function(){e.text("Event: search.bs.table")})
    $("#add").click(function () {
//        layer.open({
//            type: 2,
//            closeBtn: true,
//            shadeClose: true,
//            shade: false,
//            maxmin: true,
//            area: ['80%', '70%'],
//            content: 'staff/addInput'
//        });
        layer.open({
            type: 2,
            title: '用户管理',
            shadeClose: true,
            shade: 0.8,
            area: ['80%', '90%'],
            content: 'staff/addInput' //iframe的url
        });
    });
    $("#update").click(function () {
//        layer.open({
//            type: 2,
//            closeBtn: true,
//            title: '添加系统用户',
//            shadeClose: true,
//            shade: false,
//            maxmin: true,
//            area: ['80%', '70%'],
//            content: 'staff/updateInput'
//        });
        layer.open({
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            closeBtn: true,
            area: ['80%', '70%'],
            content: 'staff/addInput'
        });
    });
    //删除
    $("#delete").click(function () {
        swal({
            title: "您确定要删除这条信息吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        }, function () {
            swal("删除成功！", "您已经永久删除了这条信息。", "success");
        });
    });
</script>

</body>
</html>
