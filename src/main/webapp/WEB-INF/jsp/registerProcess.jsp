<%-- 
    Document   : registerProcess
    Created on : Mar 21, 2024, 6:56:00 PM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Successful</title>
     <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
</head>
<body>
    <div class="containers">
        <h2>Welcome, <%= request.getAttribute("firstname") %>!</h2>
        <p>Thank you for registering!</p>
        
        
        
        <a href="login.htm">Login</a>
    </div>
        
        
            

            
</body>
</html>