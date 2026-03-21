<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Note form</title>
</head>
<body>

<#if mode == "edit">
    <h1>Редактирование заметки</h1>
    <#assign actionUrl = "/notes/" + noteId + "/edit">
<#else>
    <h1>Создание заметки</h1>
    <#assign actionUrl = "/notes/create">
</#if>

<form method="post" action="${actionUrl}">
    <div>
        <label>Title</label><br/>
        <input type="text" name="title" value="${form.title!""}" style="width: 400px;"/>
    </div>

    <div style="margin-top: 10px;">
        <label>Content</label><br/>
        <textarea name="content" rows="8" cols="80">${form.content!""}</textarea>
    </div>

    <div style="margin-top: 10px;">
        <label>
            <input type="checkbox" name="public" <#if form.public?? && form.public>checked</#if>/>            Public
        </label>
    </div>

    <div style="margin-top: 10px;">
        <button type="submit">Save</button>
        <a href="/notes">Cancel</a>
    </div>
</form>

</body>
</html>