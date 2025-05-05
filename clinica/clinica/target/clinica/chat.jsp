<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <title>Chat</title>
    <style>
        /* Área de exibição de mensagens */
        #chatArea {
            border: 1px solid #ccc;
            height: 300px;
            overflow-y: auto;
            padding: 10px;
            background: #f9f9f9;
        }
        /* Lista de mensagens sem marcadores */
        #messages {
            list-style: none;
            margin: 0;
            padding: 0;
        }
        /* Formulário de envio */
        #chatForm {
            margin-top: 10px;
            display: flex;
        }
        #msgInput {
            flex: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px 0 0 4px;
        }
        #chatForm button {
            padding: 8px 12px;
            border: none;
            background-color: #007bff;
            color: #fff;
            border-radius: 0 4px 4px 0;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div id="chatArea">
        <ul id="messages"></ul>
    </div>
    <form id="chatForm">
        <input type="text" id="msgInput" placeholder="Digite sua mensagem..." autocomplete="off"/>
        <button type="submit">Enviar</button>
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
