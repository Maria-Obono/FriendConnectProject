<%-- 
    Document   : login
    Created on : Mar 20, 2024, 11:05:35 PM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
    <style>
        .login-container {
            background-image: url('<c:url value="/images/background.jpeg" />');
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="content">
            <h1>FriendConnect</h1>
            <p>Connect with friends and the world around<br> you on FriendConnect.</p>
        </div>
        <div class="login-form">
            <h2>Login</h2>
            <form action="loginProcess.htm" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required><br><br>
                
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required><br><br>
                
                <input type="submit" value="Login">
            </form>
            
                <a href="register.htm" class="exit-button">Don't have an account? Register here</a>
           
        </div>
    </div>
</body>
</html>