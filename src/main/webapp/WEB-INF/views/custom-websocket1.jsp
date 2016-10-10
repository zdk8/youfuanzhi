<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Home</title>
    <script src="//cdn.bootcss.com/sockjs-client/1.1.1/sockjs.min.js"></script>
    <script src="//cdn.bootcss.com/jquery/2.0.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>
<button id="stop">Stop</button>

<script>
    var sock = new SockJS('<%= request.getContextPath()%>/marcopolo');
    var stomp = Stomp.over(sock);

    stomp.connect('guest', 'guest', function (frame) {
        console.log('*****  Connected  *****');
        stomp.subscribe("/topic/marco", handlePolo);
        sayMarco();
    });

    function handleOneTime(message) {
        console.log('Received: ', message);
    }

    function handlePolo(message) {
        console.log('Received: ', message);
        $('#output').append("<b>Received: " +
                JSON.parse(message.body).message + "</b><br />")
        if (JSON.parse(message.body).message === 'Polo!') {
            setTimeout(function () {
                sayMarco()
            }, 2000);
        }
    }

    function handleErrors(message) {
        console.log('RECEIVED ERROR: ', message);
        $('#output').append("<b>GOT AN ERROR!!!: " +
                JSON.parse(message.body).message + "</b><br />")
    }

    function sayMarco() {
        console.log('Sending Marco!');
        stomp.send("/app/marco", {},
                JSON.stringify({'message': 'Marco!'}));
        $('#output').append("<b>Send: Marco!</b><br />")
    }

    $('#stop').click(function () {
        sock.close()
    });
</script>

<div id="output"></div>
</body>
</html>
