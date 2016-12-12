<%@ page import="cn.com.hvit.workspace.model.XtUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% XtUser user = (XtUser) session.getAttribute("user");%>
<% boolean dev=true;%>
<% String devText=dev?"":"";%>
<% String hvitFrontFWPrefix=dev?"http://192.168.3.102:8080/hvit-front/":"http://10.33.44.22:8080/hvit-front/";%>
<!DOCTYPE html>
<html>
<head>

    <title>丽水市防震减灾公共服务信息系统二期</title>

    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css"
          href="<%=hvitFrontFWPrefix%>js/jquery-easyui-1.5/themes/default/easyui.css">

    <link rel="stylesheet" type="text/css" href="<%=hvitFrontFWPrefix%>css/home.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>


    <%--<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>--%>
    <link rel="shortcut icon" type="image/x-icon" href="docs/images/favicon.ico"/>

    <link rel="stylesheet" href="js/libs/leafletjs/leaflet.css"/>
    <%--<script src="//cdn.bootcss.com/leaflet/1.0.1/leaflet.js"></script>--%>

    <style>
        .leaflet-control-tuli {
            /*background-color: red;
            border:1px solid black;*/
        }

        .leaflet-control-layers-toggle {
            background-image: url(images/layers.png);
            width: 36px;
            height: 36px;
        }

        /*去除自定义的关闭按钮,原先在无tab的tab中是有用 的，现在使用window自己的关闭*/
        div.window-thinborder .my-toolbar a.easyui-linkbutton[action="close"]{
            display: none;
        }
.leaflet-control-layers label {
    margin-left: 1.5em;
}

        .leaflet-control-layers-base input.menu{
            display: none;
        }

    </style>

    <script>
        <!--兼容windows无console的情况-->
        if (window.console && window.console.log) {
        } else {
            window.console = {};
            window.console.log = function () {
            };
        }
        var vecIndexOf = function (v, elt) {
            var len = v.length;
            ;
            for (var from = 0; from < len; from++) {
                if (v[from] === elt)
                    return from;
            }
            return -1;
        }

        var global = {route: {}};
        var myMap;
    </script>

</head>
<body>
<div id="header">
    <div id="header-center">
        <div id="project-name">
            <a href="framework_geoserver"><span>丽水市防震减灾公共服务信息系统<%=devText%></span></a>
        </div>
        <div id="header-menu-wrapper">
            <div id="header-menu">
                <span id="current-time"></span>
                <span>欢迎您！</span>

                <span id="current-user-name" style="cursor: pointer;"
                      isLogined="<%= (user!=null)%>"><%= user == null ? "未登录" : user.getUsername()%></span>
                <img style="max-height: 2em;margin-bottom: -1px;" src="images/user.png">
                <div style="display: inline-block;position: relative;">
                    <img id="header-triangle-down" style="max-height: 2em;margin-bottom: -1px;" src="images/向下箭头.png">
                    <div id="header-more-menu">
                        <ul>
                            <% if(user!=null && user.getLoginname().equals("admin")){%>
                            <li><a id="sysmng"><img src="images/icons/设置.png"><span>系统管理</span></a></li>
                            <% }%>
                            <li><a id="persont-setting"><img src="images/icons/设置.png"><span>账户设置</span></a></li>
                            <li><a id="logout"><img src="images/icons/退出.png"><span>退出登录</span></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="map-wrapper">
    <div id="mapid"></div>
    <div id="lishui-map-button">
    </div>
</div>

<div id="map-menu-wrapper">
    <div>图例</div>
    <ul id="map-menu"></ul>
</div>
<div id="earth-panel-wrapper">
    <div id="earth-panel">
        <div class="ep-title">
            <span class="title">地震信息</span>
            <a class="btn" id="show-history-earth-quake">历史地震</a>
        </div>
        <div class="ep-body">
            <div id="history-earthquake">
                暂无地震
            </div>
        </div>
        <div class="ep-title">
            <span class="title">台站信息</span>
            <a class="btn">运行日志</a>
        </div>
        <div class="ep-body">
            <div id="station-dg-wrapper">
                <table id="station-dg"
                       data-options="method:'get'">
                    <thead>
                    <tr>
                        <th data-options="field:'stationname',width:120">台站名称</th>
                        <th data-options="field:'flag',width:60,formatter:function(v,r,i){
                        if(v==true||v=='true'){return '<span style=&quot;color:green;&quot;>正常</span>';}
                        else{return '<span style=&quot;color:red;&quot;>异常</span>';}
                        }">状态</th>
                        <th data-options="field:'linetype',width:60,align:'center'">类型</th>
                        <th data-options="field:'unitcost',width:60,align:'center',
                        formatter:cj.button.OnlyIconFormatter(['update','查看','update'])">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
        <div class="ep-title">
            <span class="title">水库信息</span>
        </div>
        <div class="ep-body">
            <div id="shuiku-list"></div>
        </div>
    </div>

    <div id="earth-panel-hander">
        <div></div>
    </div>
</div>

</body>
</html>
<script>
    //重要的设置
    HvitFrontFWPrefix = "<%=hvitFrontFWPrefix%>";
    getPageSize=function () {
        return [10, 15, 20];
    }
</script>
<script src="<%=hvitFrontFWPrefix%>hvit-front-framework/baseConfig.js"></script>
<script src="<%=hvitFrontFWPrefix%>hvit-front-framework/require.js"
        data-main="apps/lishui2qi/app.js?v=1"></script>