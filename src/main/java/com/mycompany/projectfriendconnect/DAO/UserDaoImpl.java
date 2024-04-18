/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.DAO;

import com.mycompany.projectfriendconnect.POJO.FriendRequest;
import com.mycompany.projectfriendconnect.POJO.Login;
import com.mycompany.projectfriendconnect.POJO.FriendRequestStatus;
import com.mycompany.projectfriendconnect.POJO.User;
import com.mycompany.projectfriendconnect.exception.UsernameAlreadyExistsException;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.stream.Collectors;
import javax.persistence.NoResultException;


//import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author mariagloriaraquelobono
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {
        try {
            entityManager.persist(user);
        } catch (PersistenceException e) {
            // Handle unique constraint violation for username
            throw new UsernameAlreadyExistsException("Username already exists");
        }
    }

    @Override
    public User validateUser(Login login) {
        String query = "FROM User u WHERE u.username = :username";
        List<User> users = entityManager.createQuery(query, User.class)
                .setParameter("username", login.getUsername())
                .getResultList();

        // Check if any user was found
        if (!users.isEmpty()) {
            // Assuming only one user with a given username exists
            User user = users.get(0);

            // Check if the provided password matches the encoded password
            if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                return user; // Passwords match, return the user
            }
        }
        return null; // No user found with the given username or password mismatch
    }

    @Override
    public List<User> getAllUsersExcept(Long userId) {
        String query = "SELECT u FROM User u WHERE u.id != :userId";
        return entityManager.createQuery(query, User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void sendFriendRequest(User sender, User recipient) {

        // Create a new friend request
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setRecipient(recipient);
        friendRequest.setStatus(FriendRequestStatus.PENDING); // Set status to pending

        // Persist the friend request
        entityManager.persist(friendRequest);

    }

    @Override
    public User getUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public User findById(Long userId) {
        return entityManager.find(User.class, userId);
    }
    
    

    @Override
    public List<User> getAllFriends(Long userId) {
        // Retrieve friends where the user is the recipient and the request has been accepted
    List<User> friendsReceived = entityManager.createQuery(
            "SELECT fr.sender FROM FriendRequest fr WHERE fr.recipient.userId = :userId AND fr.status = :status",
            User.class)
            .setParameter("userId", userId)
            .setParameter("status", FriendRequestStatus.ACCEPTED)
            .getResultList();

    // Retrieve friends where the user is the sender and the request has been accepted
    List<User> friendsSent = entityManager.createQuery(
            "SELECT fr.recipient FROM FriendRequest fr WHERE fr.sender.userId = :userId AND fr.status = :status",
            User.class)
            .setParameter("userId", userId)
            .setParameter("status", FriendRequestStatus.ACCEPTED)
            .getResultList();

    // Combine the two lists and remove duplicates
    List<User> allFriends = new ArrayList<>(friendsReceived);
    allFriends.addAll(friendsSent);
    allFriends = allFriends.stream().distinct().collect(Collectors.toList());

    return allFriends;
    }

    //@Override
    //  public boolean hasSentRequest(Long senderId, Long recipientId) {
    //      Long count = entityManager.createQuery(
    //         "SELECT COUNT(r) FROM FriendRequest r " +
    //       "WHERE r.sender.userId = :senderId AND r.recipient.userId = :recipientId", Long.class)
    //       .setParameter("senderId", senderId)
    //       .setParameter("recipientId", recipientId)
    //       .getSingleResult();
    //  return count > 0;
    // }  
    @Override
    public boolean hasPendingRequest(Long senderId, Long recipientId) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(fr) FROM FriendRequest fr WHERE fr.sender.userId = :senderId AND fr.recipient.userId = :recipientId AND fr.status = :status",
                Long.class)
                .setParameter("senderId", senderId)
                .setParameter("recipientId", recipientId)
                .setParameter("status", FriendRequestStatus.PENDING)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean hasAcceptedRequest(Long senderId, Long recipientId) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(fr) FROM FriendRequest fr WHERE fr.sender.userId = :senderId AND fr.recipient.userId = :recipientId AND fr.status = :status",
                Long.class)
                .setParameter("senderId", senderId)
                .setParameter("recipientId", recipientId)
                .setParameter("status", FriendRequestStatus.ACCEPTED)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    @Transactional
    public void updateProfilePicturePath(Long userId, String profilePicturePath) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            user.setProfilePicturePath(profilePicturePath);
            entityManager.merge(user);
        }
    }

    @Override
    public String getProfilePicturePath(Long userId) {
        // Create a query to select the profile picture path for the given user ID
        String query = "SELECT u.profilePicturePath FROM User u WHERE u.userId = :userId";
        // Execute the query
        String profilePicturePath = null;
        try {
            profilePicturePath = (String) entityManager.createQuery(query)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle if no result is found
        }
        return profilePicturePath;
    }
    
//@Override
    //public void removeFriend(Long userId, Long friendId) {
      //  Session currentSession = entityManager.unwrap(Session.class);

        // Fetch the user by ID
        //User user = currentSession.find(User.class, userId);

        //if (user != null) {
            // Remove the friend from the user's friends list
           // User friendToRemove = currentSession.find(User.class, friendId);
           // if (friendToRemove != null) {
             //   user.getFriends().remove(friendToRemove);
                
                // Update the user entity in the database
               // currentSession.merge(user);
            //}
       // }
        
        // Repeat the same process for the friend's friends list to remove the user
       // User friend = currentSession.find(User.class, friendId);
        
       // if (friend != null) {
          //  User userToRemove = currentSession.find(User.class, userId);
          //  if (userToRemove != null) {
          //      friend.getFriends().remove(userToRemove);
                
                // Update the friend entity in the database
            //    currentSession.merge(friend);
           // }
       // }
   // }
 @Override
public void deleteFriend(Long userId, Long friendId) {
    // Retrieve the friend request
    FriendRequest friendRequest = entityManager.createQuery(
            "SELECT fr FROM FriendRequest fr WHERE (fr.sender.userId = :userId AND fr.recipient.userId = :friendId) OR " +
            "(fr.sender.userId = :friendId AND fr.recipient.userId = :userId) AND fr.status = :status",
            FriendRequest.class)
            .setParameter("userId", userId)
            .setParameter("friendId", friendId)
            .setParameter("status", FriendRequestStatus.ACCEPTED)
            .getSingleResult();
    
    // Delete the friend request
    entityManager.remove(friendRequest);
}


    


    @Override

    public void cancelFriendRequest(User sender, long requestId) {
        // Find the request by ID and remove it
        FriendRequest friendRequest = entityManager.find(FriendRequest.class, requestId);
        // Ensure the friend request is from the sender
        if (friendRequest.getSender().equals(sender)) {
            // Remove the friend request from the database
            entityManager.remove(friendRequest);
        }

    }
    
   
}
