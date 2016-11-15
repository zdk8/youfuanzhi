<%@ page import="cn.com.hvit.workspace.model.Ls_User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% Ls_User user=(Ls_User) request.getAttribute("user");%>
<!DOCTYPE html>
<html>
<head>

    <title>丽水市防震减灾公共服务信息系统二期</title>

    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="http://120.55.65.150/wp/jquery-easyui-1.4.5/themes/default/easyui.css">

    <link rel="stylesheet" type="text/css" href="http://hvit.in.server:3000/css/home.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>


    <%--<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>--%>
    <link rel="shortcut icon" type="image/x-icon" href="docs/images/favicon.ico"/>

    <link rel="stylesheet" href="//cdn.bootcss.com/leaflet/1.0.1/leaflet.css"/>
    <%--<script src="//cdn.bootcss.com/leaflet/1.0.1/leaflet.js"></script>--%>


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
        var my_test_mg = !true;
        var proxyUrl = function (yls) {
            return window.location.origin + '/proxyapi?yls=' + yls;
        };
        var App = {
            nav: function () {
            },
            isTest: my_test_mg,
            wrapUrl: function (url) {
                return (my_test_mg ? "test-" : "") + url
            },
            WrapIdAttribute: function (id) {
                return (my_test_mg ? "_id" : id)
            },
            tab: null,
            map: null
        };
        var Iconfig;
        var global = {};

    </script>

</head>
<body>
<div id="header">
    <div id="header-center">
        <div id="project-name">
            <span>丽水市防震减灾公共服务信息系统</span>
        </div>
        <div id="header-menu-wrapper">
            <div id="header-menu">
                <span id="current-time">2011-11-11 12:12:12</span>
                <span>欢迎您！</span>
                <span id="current-user-name" style="cursor: pointer;"
                      isLogined="<%= (user!=null)%>"><%= user==null?"未登录":user.getUsername()%></span>
                <img style="max-height: 2em;margin-bottom: -1px;" src="images/user.png">
                <div style="display: inline-block;position: relative;">
                    <img id="header-triangle-down" style="max-height: 2em;margin-bottom: -1px;" src="images/向下箭头.png">
                    <div id="header-more-menu">
                        <ul>
                            <li><a><img src="images/icons/设置.png"><span>账户设置</span></a></li>
                            <li><a><img src="images/icons/退出.png"><span>退出登录</span></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="map-wrapper">
    <div id="mapid"></div>
    <div id="button-wrapper">
        <input type="button" id="Btn1" value="Btn1" class="btnStyle span3"/>
        <input type="button" id="Btn2" value="Btn2" class="btnStyle span3"/>
        <input type="button" id="Btn3" value="Btn3" class="btnStyle span3"/>
    </div>
</div>

<div id="map-menu-wrapper">
    <ul id="map-menu"></ul>
</div>


</body>
</html>
<script>
    //重要的设置
    HvitFrontFWPrefix = "http://hvit.in.server:3000/";
</script>
<script src="http://hvit.in.server:3000/hvit-front-framework/baseConfig.js"></script>
<script src="http://hvit.in.server:3000/hvit-front-framework/require.js"
        data-main="apps/lishui2qi/app.js?v=1"></script>