<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h1>Login</h1>

<form method="post" action="/login">
    <div>
        <label>Username</label><br/>
        <input type="text" name="username"/>
    </div>

    <div>
        <label>Password</label><br/>
        <input type="password" name="password"/>
    </div>

    <div style="margin-top:10px;">
        <button type="submit">Login</button>
    </div>
</form>

<p>
    Нет аккаунта? <a href="/register">Регистрация</a>
</p>

</body>
</html>