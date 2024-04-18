<%-- 
    Document   : dashboard
    Created on : Mar 20, 2024, 8:41:09 PM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Friend List</title>
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
    <!-- Include jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="containers">
        <h2>Friend List</h2>
        <ul id="friendList">
            <c:forEach items="${friendList}" var="friend">
                <li id="friend-${friend.userId}">
                     <!-- Display the profile picture if available -->
                    <c:choose>
                        <c:when test="${not empty friend.profilePicturePath}">
                            <img src="/ProjectFriendConnect/pictures/images/${friend.userId}/${friend.imageName}.htm" alt="Profile Picture" class="picture">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/images/default-profile-picture.jpg'/>" alt="Default Profile Picture" class="picture">
                        </c:otherwise>
                    </c:choose>
                    <span class="friend-username">${friend.username}</span>
                    <div class="action-buttons-request">
                    <!-- Remove friend button with form -->
                    <form id="removeFriendForm-${friend.userId}" action="/ProjectFriendConnect/deleteFriend/${friend.userId}.htm" method="GET" style="display: inline;">
                        <input type="hidden" name="friendId" value="${friend.userId}" />
                        <input type="submit" value="UnFriend" class="remove-button" />
                    </form>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    
    <a href="welcome.htm" class="exit-button">Exit List Page</a>

    <!--  AJAX request -->
    <script>
        $(document).ready(function() {
            $("form[id^='removeFriendForm']").on("submit", function(e) {
                e.preventDefault(); // Prevent the form from submitting normally

                var form = $(this);
                var friendId = form.find("input[name='friendId']").val();

                if (confirm('Are you sure you want to remove this friend?')) {
                    $.ajax({
                        type: form.attr('method'),
                        url: form.attr('action'),
                        data: form.serialize(),
                        success: function(response) {
                            // Remove friend from the DOM
                            $("#friend-" + friendId).remove();
                        },
                        error: function(xhr) {
                            console.error(xhr.responseText);
                            alert("Error removing friend. Please try again later.");
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>
