// chatLauncher.js
document.addEventListener('DOMContentLoaded', () => {
  const ctx = window.contextPath || '';
  const launcher = `
    <button id="openChatBtn" class="chat-button">Chat</button>
    <div id="chatOverlay" class="chat-overlay">
      <div class="chat-header">
        Chat
        <button id="closeChatBtn" class="chat-close-btn">&times;</button>
      </div>
      <!-- caminho absoluto para o servlet /chat -->
      <iframe src="${ctx}/chat" class="chat-iframe"></iframe>
    </div>
  `;
  document.body.insertAdjacentHTML('beforeend', launcher);

  const overlay = document.getElementById('chatOverlay');
  const openBtn  = document.getElementById('openChatBtn');
  const closeBtn = document.getElementById('closeChatBtn');

  openBtn.addEventListener('click',  () => overlay.style.display = 'block');
  closeBtn.addEventListener('click', () => overlay.style.display = 'none');
});
