<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html><html lang="zh-CN"><head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MAGNET</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style3.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script src="js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
</head>
<script>

    const copy = async (url) => {
        try {
            $.ajax({
                type: "GET",
                url: "magnet/search/mg?uri=" + url,
                contentType : 'application/json;charset=utf-8',
                success:  function (data) {
                    console.log("magnet: " + data.data)
                    $("#copyV").val(data.data);
                    $("#copyV").select();
                    document.execCommand("copy");//执行复制操作
                    console.log('Content copied to clipboard');
                },
                error:function () {
                    layer.alert("error!");
                }
            })
        } catch (err) {
            console.error('Failed to copy: ', err);
        }
    }

    function query() {
        const kw = $("#kw").val();
        const data = {"kw": kw};

        $.ajax({
            type: "POST",
            url: "magnet/search",
            data: JSON.stringify(data),
            dataType: "json",
            contentType : 'application/json;charset=utf-8',
            success: function(data){
                $("#list").empty();
                const rs = data.data;
                if (rs.history) {
                    $("#btn-search").attr("style", "background:red")
                } else {
                    $("#btn-search").attr("style", "#2e82ff")
                }
                const results = rs.results;
                for(const result of results) {
                    let ul = "<ul class=\"list-unstyled\" style=\"margin-top: 15px;\">\n" +
                        "                    <li><span class=\"rrt common-link\" href=\"javaScript:void(0);\" onclick='copy(\""+ result.url +"\")' target=\"_blank\"><span\n" +
                        "                        class=\"highlight\">"+ result.title +"</span></span></li>\n";
                    for(const file of result.fileInfos) {
                        ul += "<li class=\"rrf\"><span><i class=\"fa fa-file-video-o\"></i>&nbsp;"+ file.fileName +"</span>\n" +
                            "                        <span class=\"rrfs\">"+ file.fileSize +"</span></li>";
                    }
                    ul += "<li class=\"rrmi\"> 收录时间:&nbsp;<span class=\"rrmiv\">"+ result.createTime +"</span> 文件大小:&nbsp;<span\n" +
                        "                        class=\"rrmiv\">"+ result.size +"</span></li>";
                    ul += "</ul>";
                    $("#list").append(ul);
                }
            },
            error:function () {
                layer.alert("error!");
            }
        })
    }
</script>
<body>
<div class="container-fluid">
<div class="row site-wrapper-border"></div>
<div class="row common-navbar-header">
    <div class="col-md-1"></div>
    <div class="col-md-6">
        <form action="#" onsubmit="query()">
            <div class="input-group" style="width: 100%"><span class="input-group-btn"><img
                    src="img/btlogo.png" height="40px"
                    style="margin-right: 8px; max-height: 90%"></a> </span> <input type="text" id="kw"
                                                                                   class="form-control search-input"
                                                                                   style="float: none"
                                                                                   placeholder="输入电影、电视、演员等磁力链接资源名称..."
                                                                                   value="">
                <span class="input-group-btn"> <button type="button" id="btn-search"
                    class="btn btn-primary search-btn" onclick="query()"> &nbsp;&nbsp;搜 索&nbsp;&nbsp; </button> </span>
            </div>
        </form>
    </div>
    <div class="col-md-3"></div>
    <div class="col-md-2"></div>
</div>
<div id="lsh" class="row" style="display: none;">
    <div class="panel panel-default visible-xs">
        <div class="panel-body" id="lsh-content" style="padding: 0px;"></div>
    </div>
</div>
<div class="row search-result-toolbar">
    <div class="col-md-1"></div>
    <div id="sop" class="col-md-6"></div>
</div>
<div class="row"></div>
<div class="row">
    <div class="col-md-1"></div>
    <div id="list" class="col-md-6">

    </div>
    <div id="right-panel" class="col-md-4 col-lg-3"></div>
    <div class="col-md-2 col-lg-3"></div>
</div>
</div><nav class="navbar-default footer">
</nav>
<input id="copyV" type="text" style="opacity: 0">
</body></html>