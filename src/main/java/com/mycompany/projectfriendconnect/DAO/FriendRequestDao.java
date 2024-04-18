/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.DAO;

import com.mycompany.projectfriendconnect.POJO.FriendRequest;
import com.mycompany.projectfriendconnect.POJO.User;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mariagloriaraquelobono
 */
@Repository
public interface FriendRequestDao {
    
    FriendRequest findById(Long id);
    
    void update(FriendRequest friendRequest);
    
    List<FriendRequest> getFriendRequestsForUser(User user);
    
     List<FriendRequest> getSentRequests(Long userId);
     
      boolean checkFriendRequestExists(Long senderId, Long recipientId);
     
    
     
     
   
    
}
