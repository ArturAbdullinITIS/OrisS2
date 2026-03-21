<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>

<h1>Register</h1>

<#if error??>
    <p style="color:red;">${error}</p>
</#if>

<form method="post" action="/register">
    <div>
        <label>Username</label><br/>
        <input type="text" name="username" value="${form.username!""}"/>
    </div>

    <div>
        <label>Password</label><br/>
        <input type="password" name="password"/>
    </div>

    <div style="margin-top:10px;">
        <button type="submit">Create account</button>
    </div>
</form>

<p>
    Уже есть аккаунт? <a href="/login">Войти</a>
</p>

</body>
</html>