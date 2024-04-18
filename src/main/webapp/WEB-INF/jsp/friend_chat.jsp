<%-- 
    Document   : chat
    Created on : Mar 20, 2024, 8:40:10 PM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>

        <title>Friend Chats</title>
        <link href="<c:url value="/css/styles.css" />" rel="stylesheet">

    </head>
    <body>
       <div class="containers">
        <h2>Friend Chats</h2>
        <ul>
            <c:forEach items="${friends}" var="friend">
                <li>
                    <!-- Display the profile picture if available -->
                    <c:choose>
                        <c:when test="${not empty friend.profilePicturePath}">
                            <img src="/ProjectFriendConnect/pictures/images/${friend.userId}/${friend.imageName}.htm" alt="Profile Picture" class="picture">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/images/default-profile-picture.jpg'/>" alt="Default Profile Picture" class="picture default-picture">
                        </c:otherwise>
                    </c:choose>
                    <a href="/ProjectFriendConnect/chat/${friend.id}.htm">${friend.firstname} ${friend.lastname}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
        
        <a href="welcome.htm" class="exit-button">Exit Friend Chat</a>

    </body>

</html>