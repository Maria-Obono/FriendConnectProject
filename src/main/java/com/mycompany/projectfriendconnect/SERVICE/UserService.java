/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.SERVICE;

import com.mycompany.projectfriendconnect.POJO.FriendRequest;
import com.mycompany.projectfriendconnect.POJO.User;
import com.mycompany.projectfriendconnect.POJO.Login;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mariagloriaraquelobono
 */
@Service
public interface UserService {

    void register(User user);

    User validateUser(Login login);

    List<User> getAllUsersExcept(Long userId);

    void sendFriendRequest(Long senderId, Long recipientId);

    User getUserById(long userId);

    boolean areFriends(Long userId1, Long userId2);

    List<User> getAllFriends(Long userId);

    boolean acceptFriendRequest(Long requestId);

    List<FriendRequest> getFriendRequestsForCurrentUser(User currentUser);

    boolean rejectFriendRequest(Long requestId);

    boolean hasPendingRequest(Long senderId, Long recipientId);

    boolean hasAcceptedRequest(Long senderId, Long recipientId);

    String uploadProfilePicture(Long userId, MultipartFile file) throws IOException;

    void updateProfilePicturePath(Long userId, String profilePicturePath);
    
    void deleteFriend(Long userId, Long friendId);
}
