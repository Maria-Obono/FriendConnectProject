/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.DAO;


import com.mycompany.projectfriendconnect.POJO.Login;
import com.mycompany.projectfriendconnect.POJO.User;

import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 *
 * @author mariagloriaraquelobono
 */
@Repository
public interface UserDao  {
    
    void register(User user);

  User validateUser(Login login);
  
  List<User> getAllUsersExcept(Long userId);
  
  void sendFriendRequest(User sender, User recipient);
  
   User getUserById(Long userId);
   
   List<User> getAllFriends(Long userId);
   
   User findById(Long userId);
   
   
   //boolean hasSentRequest(Long senderId, Long recipientId);
   
    boolean hasPendingRequest(Long senderId, Long recipientId);
    
   boolean hasAcceptedRequest(Long senderId, Long recipientId);
    
  

    
void updateProfilePicturePath(Long userId, String profilePicturePath);



// Method to retrieve the profile picture path for a user
    String getProfilePicturePath(Long userId);

   
 //void removeFriend(Long userId, Long friendId);
   // boolean removeFriend(Long userId, Long friendId);
   
void deleteFriend(Long userId, Long friendId);
    void cancelFriendRequest(User sender, long requestId);
    
}
