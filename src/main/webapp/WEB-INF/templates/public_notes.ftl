<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Публичные заметки</title>
</head>
<body>
<h1>Публичные заметки</h1>

<p>
    <a href="/notes/public">Обновить</a> |
    <a href="/notes">Мои заметки</a>
</p>

<#if notes?size == 0>
    <p>Публичных заметок нет.</p>
<#else>
    <ul>
        <#list notes as n>
            <li>
                <b>${n.title}</b><br/>
                <small>createdAt: ${n.createdAt!""}</small><br/>
                <pre style="white-space: pre-wrap;">${n.content!""}</pre>
            </li>
        </#list>
    </ul>
</#if>

</body>
</html>