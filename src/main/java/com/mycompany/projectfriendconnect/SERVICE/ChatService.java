/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.SERVICE;

import com.mycompany.projectfriendconnect.POJO.ChatRoom;
import com.mycompany.projectfriendconnect.POJO.Message;
import com.mycompany.projectfriendconnect.POJO.User;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 *
 * @author mariagloriaraquelobono
 */
@Service
public interface ChatService {
    
    
    
    ChatRoom getChatRoomById(Long roomId);
   
    
    Long getOrCreateChatRoom(Long userId1, Long userId2);
    List<Message> getMessage(Long chatRoomId);
    void sendMessage(Long chatRoomId, Long senderId, String message);
    
   User getFriendByChatRoomAndSender(Long chatRoomId, Long senderId);
    
}
