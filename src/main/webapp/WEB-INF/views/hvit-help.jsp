<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>前端框架</title>
    <link rel="shortcut icon" type="image/ico" href="images/sqwork/img/logo导航.png)"/>
    <!--[if lt IE 9]>
    <script src="js/libs/html5shiv.min.js"></script>
    <![endif]-->


    <link rel="stylesheet" type="text/css" href="http://120.55.65.150/wp/jquery-easyui-1.4.5/themes/default/easyui.css">

    <link rel="stylesheet" type="text/css" href="http://192.168.2.166:3000/css/home.css"/>

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
            map:null
        };
        var Iconfig;
        var global={};

    </script>
    <style>
        html,body{padding:0;margin:0;font-family: Verdana,Arial,Helvetica,sans-serif;}
        .nav-header,.nav-header tr,.nav-header td{
            margin:0;padding:0;
        }
        table.nav-header{
            float:left;
        }
        table.nav-header-right{
            float:right;
            font-size:16px;
            color:white;
        }
        ul.header-buttons {
            margin: 0;
            padding: 0;
            text-align: center;
            list-style-type: none;
        }
        ul.header-buttons li {
            display: inline;
            list-style-type: none;
            margin-left:20px;
        }
        ul.header-buttons li>a {
            background: #0f99f0;
            padding: 10px 15px;
            border-radius: 8px;
            cursor: pointer;
        }
        ul.header-buttons li>a:hover{
            background: #47b8ff;
        }

        .header-icon{
            padding-left:21px;
        } .header-icon-set{
              background:url(images/sqwork/ic/ic_set.png) no-repeat;
          } .header-icon-logout{
                background:url(images/sqwork/ic/ic_exit.png) no-repeat;
            }
    </style>
</head>

<body class="easyui-layout">
<div data-options="region:'north',border:false" style="margin:0;padding:0;height:80px;">
    <div style="height:80px;padding:0 50px;background:#0e89d7 url(images/sqwork/bg/bg_nav.png);
         background-size:340px 80px;background-repeat:no-repeat;box-shadow: 0 2px 10px  rgba(51, 51, 51, 0.35);;margin-bottom:2px;
         position: fixed;
    width: 100%;
    box-sizing: border-box;
         ">
        <table class="nav-header" cellspacing="0"><tr>
            <td style="vertical-align: middle;height: 80px;">
                <div style="height:48px;width:48px;background:url(images/sqwork/img/logo导航.png);background-size:48px 48px;">
                </div>
            </td>
            <td><span style="font-size: 20px;color: white;padding-left:5px;">前端</span></td>
        </tr></table>
        <table class="nav-header nav-header-right" cellspacing="0"><tr>
            <td style="vertical-align: middle;height: 80px;">
                您好：张三</a>
            </td>
            <td>
                <ul class="header-buttons">

                    <li><a ><span class="header-icon header-icon-set"><span>设置</span></span></a></li>

                    <li><a ><span class="header-icon header-icon-logout"><span>退出</span></span></a></li>

                </ul>
            </td>
        </tr></table>
    </div>

</div>
<div data-options="region:'west',split:false,border:0" style="position:relative;width:200px;background:url(images/sqwork/bg/bg_menu.png) #0c88d6;">
    <!--<ul id="main-menu-tree" class="easyui-tree" data-options="url:'menutree',method:'post',animate:true,dnd:true" menutree></ul>-->
    <div style="background:#017ecd;font-size:16px;padding-top:20px;padding-bottom:20px;text-align:center;color:white;">首页</div>
    <!--
            <div style="position:fixed;bottom:0;background:url(images/sqwork/bg/bg_menu.png);width:200px;height:260px;"></div>
    -->
    <ul id="main-menu-tree2" style="position:relative;"></ul>

</div>
<div data-options="region:'center',border:0">
    <div id="maintab" style="border-width:0;"></div>
</div>
<div id="to-be-remove"  style="width:200px;height:500px;background:#0c88d6;"></div>
</body>
</html>



<script src="http://192.168.2.166:3000/hvit-front-framework/baseConfig.js"></script>
<script src="http://192.168.2.166:3000/hvit-front-framework/require.js"
        data-main="apps/hvit-help/app.js?v=1" bb="1468464346238"></script>