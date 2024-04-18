/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.DAO;

import com.mycompany.projectfriendconnect.POJO.ChatRoom;
import com.mycompany.projectfriendconnect.POJO.Message;


import com.mycompany.projectfriendconnect.POJO.User;
import java.util.List;



import org.springframework.stereotype.Repository;

/**
 *
 * @author mariagloriaraquelobono
 */
@Repository
public interface ChatDao {
    
    
    
    ChatRoom getChatRoomById(Long chatRoomId);
    
    
    
    Long getOrCreateChatRoom(Long userId1, Long userId2);
    
    List<Message> getMessage(Long chatRoomId);
    
    
    void saveMessage(Message message);
    
    User getFriendByChatRoomAndSender(Long chatRoomId, Long senderId);

    
}
