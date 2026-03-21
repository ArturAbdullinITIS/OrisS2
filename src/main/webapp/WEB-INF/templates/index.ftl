<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
</head>
<body>

<h1>Главная</h1>

<ul>
    <li><a href="/notes">Мои заметки (список + редактирование + удаление)</a></li>
    <li><a href="/notes/create">Создать заметку</a></li>
    <li><a href="/notes/public">Публичные заметки</a></li>
    <li><a href="/admin/notes">Admin: все заметки (JSON)</a></li>
</ul>

<form method="post" action="/logout">
    <button type="submit">Logout</button>
</form>

</body>
</html>