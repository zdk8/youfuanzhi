define(['underscore','text!mapviews/LastestEarthQuake-template.htm','views/TipManager'],function (_,htm,TipManager) {
    infoSample = {lat:30,lon:120,location:'XXXXXXXXXXXXXXx',stime:'2012-09-09',time:'2012-09-09 12:11:10',
        M:9,Ms:10,depth:20.99,cname:'国家台网',code:'CD'};
    var wsUri = "ws://10.33.5.242:3001/lumprj/";

    var tpl = _.template(htm);
    var websocket = new WebSocket(wsUri);
    var mark;
    var myIcon = L.icon({
        iconUrl: 'images/icons/6.gif',
        iconSize: [50, 50]
    });

    var showMark = function (info) {
        if (mark) {
            myMap.removeLayer(mark);
        }

        var content = tpl($.extend({}, info, {
            Ml: (function () {
                if (info.Ml) {
                    return info.Ml.toFixed(1);
                }
                return "无";
            })(),
            Ms: (function () {
                if (info.Ms) {
                    return info.Ms.toFixed(1);
                }
                return "无";
            })(), M: (function () {
                if (info.M) {
                    return info.M.toFixed(1);
                }
                return "无";
            })()
        }));
        mark = L.marker([info.lat, info.lon], {icon: myIcon, zIndexOffset: 500}).addTo(myMap).bindPopup(content).openPopup();

        myMap.setView([info.lat, info.lon]);
        require(['mapviews/OneHistoryQuake'],function (OneHistoryQuake) {
            OneHistoryQuake.showHtml(content);
        });
    };

    ShowMark=showMark;

    var onMessage = function (evt) {
        var info = JSON.parse(evt.data);
        if (info.type === "eqim") {
            showMark(info);
        }
    };

    websocket.onopen = function (evt) {
        TipManager.addTip('提示', '最新地震服务器连接正常');
/*        $.messager.show({
            title: '提示',
            msg: '最新地震服务器连接正常',
            showType: 'show',
            timeout: 1000,
            style: {
                right: '',
                bottom: ''
            }
        });*/
    };

    websocket.onclose = function (evt) {
        TipManager.addTip('提示', '连接断开，正在尝试建立连接。。。');
/*        $.messager.show({
            title: '提示',
            msg: '连接断开，正在尝试建立连接。。。',
            showType: 'show',
            timeout: 3000,
            style: {
                right: '',
                bottom: ''
            }
        });*/
    };

    websocket.onmessage = function (evt) {
        onMessage(evt)
    };

    websocket.onerror = function (evt) {
        TipManager.addTip('提示', '连接error！');
//        alert("连接error！");
    };

    TipManager.addTip('提示', '加载中');
});