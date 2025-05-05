from fastapi import FastAPI, WebSocket
from fastapi.responses import HTMLResponse
import uvicorn

app = FastAPI()

# Lista para armazenar conexões WebSocket ativas
connections = []

# HTML da interface do chat (simples e em uma única página)
html = """
<!DOCTYPE html>
<html>
<head>
    <title>Chat com WebSocket</title>
</head>
<body>
    <h2>Chat em tempo real</h2>
    <input type="text" id="messageText" placeholder="Digite sua mensagem" />
    <button onclick="sendMessage()">Enviar</button>
    <ul id="messages"></ul>

    <script>
        const ws = new WebSocket("ws://localhost:8000/ws");

        ws.onmessage = function(event) {
            const messages = document.getElementById('messages');
            const message = document.createElement('li');
            message.textContent = event.data;
            messages.appendChild(message);
        };

        function sendMessage() {
            const input = document.getElementById("messageText");
            ws.send(input.value);
            input.value = "";
        }
    </script>
</body>
</html>
"""

# Rota HTTP padrão que retorna a interface do chat
@app.get("/")
async def get():
    return HTMLResponse(html)

# WebSocket endpoint
@app.websocket("/ws")
async def websocket_endpoint(websocket: WebSocket):
    await websocket.accept()
    connections.append(websocket)
    try:
        while True:
            data = await websocket.receive_text()
            # Envia a mensagem para todos os clientes conectados
            for conn in connections:
                await conn.send_text(data)
    except:
        connections.remove(websocket)

# Executa o servidor
if __name__ == "__main__":
    uvicorn.run("chat:app", host="127.0.0.1", port=8000, reload=True)
