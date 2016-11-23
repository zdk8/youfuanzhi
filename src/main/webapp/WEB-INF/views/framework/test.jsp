<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>
    <script src="js/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="js/libs/sockjs-client/1.1.1/sockjs.min.js"></script>
    <script src="js/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>
<button id="stop">Stop</button>
<button id="all">通知所有在线设备</button>
<textarea id="all-message"></textarea>

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
            }, 20000);
        }

        if(JSON.parse(message.body).message.startsWith('warning:')) {
            var msg=JSON.parse(message.body).message;
            alert(msg);
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

    $('#all').click(function () {
                $('#output').html("<b>通知所有已知设备</b><br />")
        stomp.send("/app/marco", {},
                JSON.stringify({'message': 'All:'+$('#all-message').val()}));

    });

</script>

<div id="output"></div>
</body>
</html>
