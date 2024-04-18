<%-- 
    Document   : register
    Created on : Mar 20, 2024, 11:04:42 PM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register Page</title>
     <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Create Account</h2>
        <form action="registerProcess.htm" method="post">
            
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>
            <!-- Display error message if exists -->
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required minlength="6"><br>
            
            
            <label for="firstname">First Name:</label>
            <input type="text" id="firstname" name="firstname" required><br>
            
            
            <label for="lastname">Last Name:</label>
            <input type="text" id="lastname" name="lastname" required><br>
            
            
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" required pattern="^[a-zA-Z0-9._%+-]+@(gmail|hotmail|outlook|yahoo)\.(com|net|org|edu)$"><br>
            
            <label for="address">Address:</label>
            <input type="text" id="address" name="address"><br>
            
            <label for="phone">Phone Number:</label>
            <input type="tel" id="phone" name="phone" pattern="[0-9]{10}"><br>
            
            <input type="submit" value="Register">
            
            
        </form>
    </div>
    
    
            <a href="login.htm" class="exit-button">Go Back to Login</a>
        
</body>
</html>