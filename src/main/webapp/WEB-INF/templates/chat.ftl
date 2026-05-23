<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Chat</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 900px; margin: 32px auto; padding: 0 16px; }
        header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 24px; }
        nav a { margin-left: 12px; }
        #messages { border: 1px solid #d0d7de; min-height: 360px; padding: 16px; margin-bottom: 16px; }
        .message { padding: 10px 0; border-bottom: 1px solid #eef1f4; }
        .meta { color: #57606a; font-size: 13px; margin-bottom: 4px; }
        form { display: flex; gap: 8px; }
        input { flex: 1; padding: 10px; }
        button { padding: 10px 16px; cursor: pointer; }
    </style>
</head>
<body>
<header>
    <h1>Chat</h1>
    <nav>
        <a href="/chat/public">Public history</a>
        <a href="/chat/my">My messages</a>
    </nav>
</header>

<div id="messages">
    <#list messages as message>
        <div class="message" data-id="${message.id}">
            <div class="meta">${message.authorUsername} · ${message.sentAt}</div>
            <div>${message.content?html}</div>
        </div>
    </#list>
</div>

<form id="message-form">
    <input id="message-content" name="content" maxlength="1000" autocomplete="off" placeholder="Message" required>
    <button type="submit">Send</button>
</form>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7/bundles/stomp.umd.min.js"></script>
<script>
    const messages = document.getElementById('messages');
    const form = document.getElementById('message-form');
    const input = document.getElementById('message-content');
    const stompClient = new StompJs.Client({
        webSocketFactory: () => new SockJS('/ws'),
        reconnectDelay: 5000
    });

    function escapeHtml(value) {
        const div = document.createElement('div');
        div.textContent = value;
        return div.innerHTML;
    }

    function appendMessage(message) {
        const row = document.createElement('div');
        row.className = 'message';
        row.dataset.id = message.id;
        row.innerHTML = '<div class="meta">' + escapeHtml(message.authorUsername) + ' · ' + escapeHtml(message.sentAt) + '</div>'
            + '<div>' + escapeHtml(message.content) + '</div>';
        messages.appendChild(row);
        messages.scrollTop = messages.scrollHeight;
    }

    stompClient.onConnect = () => {
        stompClient.subscribe('/topic/messages', frame => appendMessage(JSON.parse(frame.body)));
    };

    form.addEventListener('submit', event => {
        event.preventDefault();
        const content = input.value.trim();
        if (!content || !stompClient.connected) {
            return;
        }
        stompClient.publish({
            destination: '/app/send',
            body: JSON.stringify({ content })
        });
        input.value = '';
    });

    stompClient.activate();
</script>
</body>
</html>
