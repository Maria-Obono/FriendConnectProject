<%-- 
    Document   : chat_room
    Created on : Apr 1, 2024, 9:24:11 PM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Chat Room</title>
        <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    </head>
    <body>
        <div class="containers">
            <h2>Chat with ${friend.username}</h2>
            <div id="chat-messages">
                <c:forEach items="${messages}" var="message">
                    <c:choose>
                        <c:when test="${message.senderId eq senderId}">
                            <div class="message me">
                                <p>${message.messageText}</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="message friend">
                                <!-- Display the profile picture if available -->
                                <c:choose>
                                    <c:when test="${not empty friend.profilePicturePath}">
                                        <img src="/ProjectFriendConnect/pictures/images/${friend.userId}/${friend.imageName}.htm" alt="Profile Picture" class="picture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value='/images/default-profile-picture.jpg'/>" alt="Default Profile Picture" class="picture default-picture">
                                    </c:otherwise>
                                </c:choose>
                                <p>${message.messageText}</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <form class="texting-box" action="/ProjectFriendConnect/sendMessage.htm" method="post">
                <input type="hidden" name="chatRoomId" value="${chatRoomId}" />
                <input type="hidden" name="senderId" value="${senderId}" />
                <textarea name="message" placeholder="Type your message here"></textarea>
                <button type="submit">Send</button>
            </form>
        </li>
    </div>
    <a href="/ProjectFriendConnect/friendsChat.htm" class="exit-button">Exit Chat Room</a>
    
    <script>
        // Keep scroll position at the bottom of the message box
        var chatMessages = document.getElementById('chat-messages');
        chatMessages.scrollTop = chatMessages.scrollHeight;

        // Handle form submission using AJAX
        document.getElementById('sendMessageForm').addEventListener('submit', function(event) {
            // Prevent default form submission
            event.preventDefault();
            
            // Get the form data
            var formData = new FormData(this);
            
            // Send AJAX request
            fetch(this.action, {
                method: this.method,
                body: formData
            })
            .then(response => response.text())
            .then(data => {
                // Add the new message to the chat box
                var messageDiv = document.createElement('div');
                messageDiv.className = 'message me';
                messageDiv.innerHTML = `<p>${formData.get('message')}</p>`;
                chatMessages.appendChild(messageDiv);
                
                // Keep scroll position at the bottom of the message box
                chatMessages.scrollTop = chatMessages.scrollHeight;
                
                // Clear the textarea
                this.reset();
            })
            .catch(error => {
                // Display error message
                alert("Error: " + error);
            });
        });
    </script>
</body>


</html>
