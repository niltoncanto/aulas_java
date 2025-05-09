<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <title>Chat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <div id="chatArea">
        <ul id="messages"></ul>
    </div>
    <form id="chatForm">
        <input type="text" id="msgInput" placeholder="Digite sua mensagem..." autocomplete="off"/>
        <button type="submit" id="buttonchat">Enviar</button>
    </form>

    <script>
        var protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
        var socket = new WebSocket(protocol + '//' + window.location.host + '${pageContext.request.contextPath}/chatws');

        socket.addEventListener('open', function() {
            console.log('WebSocket conectado.');
        });
        socket.addEventListener('close', function() {
            console.log('WebSocket desconectado.');
        });
        socket.addEventListener('message', function(event) {
            var messages = document.getElementById('messages');
            var li = document.createElement('li');
            li.textContent = event.data;
            messages.appendChild(li);
            document.getElementById('chatArea').scrollTop = document.getElementById('chatArea').scrollHeight;
        });

        document.getElementById('chatForm').addEventListener('submit', function(e) {
            e.preventDefault();
            var input = document.getElementById('msgInput');
            if (input.value.trim() !== '') {
                socket.send(input.value);
                input.value = '';
            }
        });
    </script>
</body>
</html>
