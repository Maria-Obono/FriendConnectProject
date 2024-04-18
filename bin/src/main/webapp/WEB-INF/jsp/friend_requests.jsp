<%-- 
    Document   : rejectRequest
    Created on : Mar 26, 2024, 4:13:19 AM
    Author     : mariagloriaraquelobono
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Friend Requests</title>
        <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script>
            $(document).ready(function () {
                // Submit form using Ajax (remains unchanged)
                $('form').submit(function (e) {
                    // Ajax submission code remains unchanged
                });

                // Handle successful form submission
                $('form').on('submit', function (e) {
                    e.preventDefault();
                    var form = $(this);
                    var listItem = form.closest('li');

                    $.ajax({
                        url: form.attr('action'),
                        type: form.attr('method'),
                        data: form.serialize(),
                        success: function (response) {
                            // Display success message dynamically
                            listItem.find('.success-message').html(response);
                            listItem.find('.error-message').empty();

                            // Remove the list item from the page
                            listItem.fadeOut('fast', function () {
                                $(this).remove();
                            });
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            // Display error message dynamically
                            listItem.find('.error-message').html(xhr.responseText);
                            listItem.find('.success-message').empty();
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <div class="containers">
            <h2>Friend Requests</h2>
            <ul>
                <c:if test="${not empty friendRequests}">
                    <c:forEach items="${friendRequests}" var="request">
                        <li>
                            <!-- Display the profile picture if available -->
                            <c:choose>
                                <c:when test="${not empty request.sender.profilePicturePath}">
                                    <img src="/ProjectFriendConnect/pictures/images/${request.sender.userId}/${request.sender.imageName}.htm" alt="Profile Picture" class="picture">
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value='/images/default-profile-picture.jpg'/>" alt="Default Profile Picture" class="picture default-picture">
                                </c:otherwise>
                            </c:choose>



                            <span class="friend-username">${request.sender.firstname} ${request.sender.lastname}</span>.
                            <div class="action-buttons-request">
                                <form action="/ProjectFriendConnect/acceptFriendRequest.htm" method="post">
                                    <input type="hidden" name="requestId" value="${request.id}" />
                                    <button type="submit" class="accept-button">Accept</button>
                                </form>
                                <form action="/ProjectFriendConnect/rejectFriendRequest.htm" method="post">
                                    <input type="hidden" name="requestId" value="${request.id}" />
                                    <button type="submit" class="reject-button">Reject</button>
                                </form>
                            </div>
                            <div class="success-message"></div>
                            <div class="error-message"></div>
                        </li>
                    </c:forEach>
                </c:if>
                <c:if test="${empty friendRequests}">
                    <li>${noRequestsMessage}</li>
                    </c:if>
            </ul>
        </div>

        <a href="welcome.htm" class="exit-button">Exit Requests Page</a>

    </body>


</html>
