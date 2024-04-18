<%-- 
    Document   : index
    Created on : Mar 26, 2024, 4:06:58 PM
    Author     : mariagloriaraquelobono
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>Find Friends</title>
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet">

   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            // Handle form submission using AJAX
            $("form[id^='friendRequestForm']").submit(function(event) {
                // Prevent default form submission
                event.preventDefault();
                
                // Get the form data
                var formData = $(this).serialize();
                
                // Store the form element in a variable for later use
                var form = $(this);
                
                // Send AJAX request
                $.ajax({
                    url: form.attr('action'),
                    type: form.attr('method'),
                    data: formData,
                    success: function(response) {
                        // Update status
                        var userId = form.find('input[name="recipientId"]').val();
                        $('#status-' + userId).text(response);
                        
                        // Remove the user from the list
                         form.closest('li').remove();
                    },
                    error: function(xhr) {
                        // Display error message
                        alert("Error: " + xhr.responseText);
                    }
                });
            });
        });
    </script>

</head>
<body>
    <div class="containers">
        <h2>Friend Suggestions</h2>
        <ul>
            <c:forEach items="${users}" var="user">
                <li>
                    <!-- Display the profile picture if available -->
                    <c:choose>
                        <c:when test="${not empty user.profilePicturePath}">
                            <img src="/ProjectFriendConnect/pictures/images/${user.userId}/${user.imageName}.htm" alt="Profile Picture" class="picture">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/images/default-profile-picture.jpg'/>" alt="Default Profile Picture" class="picture">
                        </c:otherwise>
                    </c:choose>
             
                    <span class="friend-username">${user.firstname} ${user.lastname}</span>
                    <div class="action-buttons-request">
                    <form id="friendRequestForm-${user.id}" method="post" action="/ProjectFriendConnect/sendFriendRequest.htm">
                        <input type="hidden" name="recipientId" value="${user.id}" />
                        <button type="submit">Send Friend Request</button>
                        <span class="status" id="status-${user.id}"></span>
                    </form>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    
     <a href="welcome.htm" class="exit-button">Exit Suggestion Page</a>
</body>
</html>
