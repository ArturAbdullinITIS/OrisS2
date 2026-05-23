<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Public chat history</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 900px; margin: 32px auto; padding: 0 16px; }
        .message { padding: 12px 0; border-bottom: 1px solid #eef1f4; }
        .meta { color: #57606a; font-size: 13px; margin-bottom: 4px; }
    </style>
</head>
<body>
<h1>Public chat history</h1>
<#list messages as message>
    <div class="message">
        <div class="meta">${message.authorUsername} · ${message.sentAt}</div>
        <div>${message.content?html}</div>
    </div>
<#else>
    <p>No messages yet.</p>
</#list>
</body>
</html>
