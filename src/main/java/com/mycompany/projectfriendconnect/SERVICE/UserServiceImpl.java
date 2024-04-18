/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.SERVICE;

//import com.mycompany.projectfriendconnect.service.*;
//import com.mycompany.projectfriendconnect.dao.UserDao;
//import com.mycompany.projectfriendconnect.model.Login;
//import com.mycompany.projectfriendconnect.model.User;
import com.mycompany.projectfriendconnect.DAO.FriendRequestDao;
import com.mycompany.projectfriendconnect.POJO.User;
import com.mycompany.projectfriendconnect.DAO.UserDao;
import com.mycompany.projectfriendconnect.POJO.FriendRequest;
import com.mycompany.projectfriendconnect.POJO.FriendRequestStatus;

import com.mycompany.projectfriendconnect.POJO.Login;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mariagloriaraquelobono
 */
@Service
@Repository
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final FriendRequestDao friendRequestDao;

    private static final String UPLOAD_DIRECTORY = "/Users/mariagloriaraquelobono/FriendConnect-Photos/";

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, FriendRequestDao friendRequestDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.friendRequestDao = friendRequestDao;

    }

    @Override
    @Transactional
    public void register(User user) {
        // Encrypt the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.register(user);
    }

    @Override
    @Transactional
    public User validateUser(Login login) {
        //return userDao.validateUser(login);
        User user = userDao.validateUser(login);
        if (user != null && passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    @Override
    @Transactional
    public List<User> getAllUsersExcept(Long userId) {
        // Implementation to retrieve all users except the logged-in user
        List<User> users = userDao.getAllUsersExcept(userId);
        return users;
    }

    @Override
    @Transactional
    public void sendFriendRequest(Long senderId, Long recipientId) {
        // Retrieve sender and recipient from the database
        User sender = userDao.getUserById(senderId);
        User recipient = userDao.getUserById(recipientId);

        // Check if sender and recipient exist
        if (sender != null && recipient != null) {
            // Assuming you have a method in UserDao to handle friend requests
            userDao.sendFriendRequest(sender, recipient);
        } else {
            // Handle case where sender or recipient is not found

            System.err.println("Sender or recipient not found");
        }
    }

    @Override
    @Transactional
    public List<FriendRequest> getFriendRequestsForCurrentUser(User currentUser) {

        return friendRequestDao.getFriendRequestsForUser(currentUser);
    }

    @Override
    @Transactional
    public boolean rejectFriendRequest(Long requestId) {
        // Retrieve the friend request by ID
        FriendRequest friendRequest = friendRequestDao.findById(requestId);

        if (friendRequest != null) {
            // Update the status of the friend request to indicate rejection
            friendRequest.setStatus(FriendRequestStatus.REJECTED);
            // Update the friend request in the database
            friendRequestDao.update(friendRequest);
            return true; // Successfully rejected friend request
        } else {
            return false; // Friend request not found
        }
    }

    @Override
    @Transactional
    public boolean acceptFriendRequest(Long requestId) {
        // Assume you have a method in your data access layer to retrieve the friend request by ID
        FriendRequest friendRequest = friendRequestDao.findById(requestId);

        if (friendRequest != null) {
            // Update the status of the friend request to indicate acceptance
            friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
            // Assume you have a method in your data access layer to update the friend request
            friendRequestDao.update(friendRequest);
            return true; // Successfully accepted friend request
        } else {
            return false; // Friend request not found
        }
    }

    @Override
    public Set<Long> getSentRequestUserIds(Long userId) {
        Set<Long> sentRequestUserIds = new HashSet<>();
        List<FriendRequest> sentRequests = friendRequestDao.getSentRequests(userId);
        for (FriendRequest request : sentRequests) {
            sentRequestUserIds.add(request.getRecipient().getId());
        }
        return sentRequestUserIds;
    }

    @Override
    @Transactional
    public Set<User> getSentRequestsUsers(Long userId) {
        User currentUser = userDao.getUserById(userId);
        Set<User> sentRequestsUsers = currentUser.getSentRequests().stream()
                .map(FriendRequest::getRecipient)
                .collect(Collectors.toSet());
        return sentRequestsUsers;
    }

    @Override
    @Transactional
    public List<User> getAllUsersExceptAndNotSentRequests(Long userId, Set<User> sentRequestsUsers) {
        List<User> allUsersExceptCurrentUser = userDao.getAllUsersExcept(userId);

        // Remove users to whom requests have already been sent
        allUsersExceptCurrentUser.removeAll(sentRequestsUsers);

        return allUsersExceptCurrentUser;
    }

    @Override
    @Transactional
    public void cancelFriendRequest(User sender, long requestId) {
        userDao.cancelFriendRequest(sender, requestId);
    }

    @Override
    @Transactional
    public boolean areFriends(Long userId1, Long userId2) {
        return friendRequestDao.checkFriendRequestExists(userId1, userId2)
                && friendRequestDao.checkFriendRequestExists(userId2, userId1);
    }

    @Override
    public List<User> getAllFriends(Long userId) {
        return userDao.getAllFriends(userId);
    }

    //@Override
    //public boolean hasSentRequest(Long senderId, Long recipientId) {
    //    return userDao.hasSentRequest(senderId, recipientId);
    // }
    @Override
    public boolean hasPendingRequest(Long senderId, Long recipientId) {
        return userDao.hasPendingRequest(senderId, recipientId);
    }

    @Override
    public boolean hasAcceptedRequest(Long senderId, Long recipientId) {
        return userDao.hasAcceptedRequest(senderId, recipientId);
    }

    @Override
    public String uploadProfilePicture(Long userId, MultipartFile file) throws IOException {

        String directoryPath = UPLOAD_DIRECTORY + "images/" + userId + "/";

        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Define the file path relative to the webapp directory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = directoryPath + fileName;
        // Save the file to the specified path
        File dest = new File(filePath);
        file.transferTo(dest);
        // Return the relative file path
        return filePath;
    }

    @Override
    public void updateProfilePicturePath(Long userId, String profilePicturePath) {
        userDao.updateProfilePicturePath(userId, profilePicturePath);
    }

    @Override
    public String getProfilePictureURL(Long userId) {
        User user = userDao.findById(userId);
        if (user != null) {
            return user.getProfilePicturePath();
        }
        return null;
    }
    
  //@Override
   // public void removeFriend(Long userId, Long friendId) {
   //     userDao.removeFriend(userId, friendId);
   // }
    
    //@Override
    //public boolean removeFriend(Long userId, Long friendId) {
    //    return userDao.removeFriend(userId, friendId);
    //}
    
     @Override
    public void deleteFriend(Long userId, Long friendId) {
        userDao.deleteFriend(userId, friendId);
    }
                
}
