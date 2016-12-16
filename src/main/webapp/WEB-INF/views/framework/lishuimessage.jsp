<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Home</title>
    <script src="js/libs/sockjs-client/1.1.1/sockjs.min.js"></script>
    <script src="js/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="js/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>


<button id="stop">Stop</button>
<button id="send">send</button>

<script>
    //var TipManager=parent.TipManager;
    var TipManager={};
    TipManager.addTip=function (title,message) {

    }
    TipManager=parent.window.TipManager;

    var sock = new SockJS('<%= request.getContextPath()%>/marcopolo');
    var stomp = Stomp.over(sock);

    stomp.connect('guest', 'guest', function (frame) {
        stomp.subscribe("/topic/marco", handlePolo);
    });


    function handlePolo(resp) {
        var text=$.parseJSON(resp.body).message;
        $('#output').append("<b>Received: "+text + "</b><br />");
        TipManager.addTip('爆破备案', text.substr("blast".length));
    }


    function sayMarco() {
        stomp.send("/app/marco", {},JSON.stringify({'message': 'Marco!'}));
        $('#output').append("<b>Send: Marco!</b><br />")
    }

    $('#stop').click(function () {
        sock.close();
    });

    $('#send').click(function () {
        sayMarco();
    });
</script>

<div id="output"></div>
</body>
</html>
