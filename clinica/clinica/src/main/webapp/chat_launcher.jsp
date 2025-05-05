
<%@ page contentType="text/html; charset=UTF-8" %>
<%--
  Fragmento para injetar o botão flutuante e o overlay de chat.
  Deve ser incluído ao final do body em cada página que usa o template.
--%>
<button id="openChatBtn" class="chat-button">Chat</button>

<div id="chatOverlay" class="chat-overlay">
    <div class="chat-header">
        Chat
        <button id="closeChatBtn" class="chat-close-btn">&times;</button>
    </div>
    <iframe src="${pageContext.request.contextPath}/chat" class="chat-iframe"></iframe>
</div>

<script>
(function() {
    var openBtn = document.getElementById('openChatBtn');
    var closeBtn = document.getElementById('closeChatBtn');
    var overlay = document.getElementById('chatOverlay');
    if (!openBtn || !closeBtn || !overlay) {
        console.warn('Chat launcher: elementos não encontrados');
        return;
    }
    openBtn.addEventListener('click', function() {
        overlay.style.display = 'block';
    });
    closeBtn.addEventListener('click', function() {
        overlay.style.display = 'none';
    });
})();
</script>
