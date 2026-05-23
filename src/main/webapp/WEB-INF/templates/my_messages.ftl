<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>My messages</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 900px; margin: 32px auto; padding: 0 16px; }
        header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 24px; }
        .message { display: flex; justify-content: space-between; gap: 16px; padding: 12px 0; border-bottom: 1px solid #eef1f4; }
        .meta { color: #57606a; font-size: 13px; margin-bottom: 4px; }
        button { padding: 8px 12px; cursor: pointer; }
    </style>
</head>
<body>
<header>
    <h1>My messages</h1>
    <a href="/chat">Back to chat</a>
</header>

<#list messages as message>
    <div class="message">
        <div>
            <div class="meta">${message.sentAt}</div>
            <div>${message.content?html}</div>
        </div>
        <form method="post" action="/chat/${message.id}/delete">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <button type="submit">Delete</button>
        </form>
    </div>
<#else>
    <p>No messages yet.</p>
</#list>
</body>
</html>
