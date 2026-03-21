<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Мои заметки</title>
</head>
<body>
<h1>Мои заметки</h1>

<p>
    <a href="/notes/create">Создать заметку</a> |
    <a href="/notes/public">Посмотреть публичные</a>
</p>

<#if notes?size == 0>
    <p>Заметок нет.</p>
<#else>
    <ul>
        <#list notes as n>
            <li>
                <b>${n.title}</b>
                <#if n.public>
                    <span>(public)</span>
                <#else>
                    <span>(private)</span>
                </#if>
                <br/>
                <small>createdAt: ${n.createdAt!""}</small>
                <br/>

                <a href="/notes/${n.id}/edit">edit</a>

                <form method="post" action="/notes/${n.id}/delete" style="display:inline;">
                    <button type="submit">delete</button>
                </form>
            </li>
        </#list>
    </ul>
</#if>

</body>
</html>