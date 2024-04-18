<%-- 
    Document   : welcome
    Created on : Mar 20, 2024, 11:06:05 PM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <head>

        <title>Welcome</title>
        <link href="<c:url value="/css/styles.css" />" rel="stylesheet">

    </head>
    <body>
        <div class="containers">
            <div class="header">
                <div class="welcome-message">
                    <h2>Welcome ${firstname}!</h2>
                    
            <a href="login.htm" class="exit-button">Log out</a>
            
                </div>

                <h1 class="logo">FriendConnect</h1>
            </div>
            <!-- Display the profile picture -->
            <c:choose>
                <c:when test="${not empty profilePicturePath}">
                    <div class="profile-upload">
                        <div class="profile-picture-container">
                            <img src="/ProjectFriendConnect/pictures/images/${userId}/${imageName}.htm" alt="Profile Picture" class="profile-picture">
                            <img src="<c:url value='/images/camera.png'/>" alt="Default Profile Picture" class="default-picture" onclick="document.getElementById('fileInput').click()">
                        </div>
                        <form action="uploadProfilePicture.htm" method="post" enctype="multipart/form-data">
                            <input type="file" name="file" id="fileInput" accept="image/*" required style="display: none;">
                            <input type="submit" value="Save Picture">
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="profile-upload">
                        <div class="default-picture-container">
                            <img src="<c:url value='/images/default-profile-picture.jpg'/>" alt="Default Profile Picture" class="profile-picture" >
                            <img src="<c:url value='/images/camera.png'/>" alt="Default Profile Picture" class="default-picture" onclick="document.getElementById('fileInput').click()">
                        </div>
                        <form action="uploadProfilePicture.htm" method="post" enctype="multipart/form-data">
                            <input type="file" name="file" id="fileInput" accept="image/*" required style="display: none;">
                            <input type="submit" value="Save Picture">
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="action-buttons">
                <a href="findFriends.htm" class="action-button">
                 <img src="<c:url value='/images/find-friends.avif'/>" alt="Find Friends" class="profile-picture">   
                    <br>Friend Suggestions
                </a>
                <a href="friendRequests.htm" class="action-button">
                    <img src="<c:url value='/images/friend-requests.jpeg'/>" alt="View Friend Requests" class="profile-picture">
                    <br>View Friend Requests
                </a>
                <a href="friendsChat.htm" class="action-button">
                    <img src="<c:url value='/images/chat-icon-images.png'/>" alt="Chats" class="profile-picture">
                    <br>Chats
                </a>
                <a href="friendList.htm" class="action-button">
                   <img src="<c:url value='/images/friends.jpeg'/>" alt="Friends" class="profile-picture">
                    <br>Friends
                </a>
            </div>

        </div>
        
    </body>
</html>
