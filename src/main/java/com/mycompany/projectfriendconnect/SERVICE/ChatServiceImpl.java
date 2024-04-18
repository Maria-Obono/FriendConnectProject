/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.SERVICE;

import com.mycompany.projectfriendconnect.DAO.ChatDao;
import com.mycompany.projectfriendconnect.DAO.UserDao;
import com.mycompany.projectfriendconnect.POJO.ChatRoom;
import com.mycompany.projectfriendconnect.POJO.ChatRoomNotFoundException;

import com.mycompany.projectfriendconnect.POJO.Message;

import com.mycompany.projectfriendconnect.POJO.User;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

/**
 *
 * @author mariagloriaraquelobono
 */
@Service
@Transactional
public class ChatServiceImpl implements ChatService {
    
    @Autowired
    private ChatDao chatDao;
    
    

   

    

    @Override
    public ChatRoom getChatRoomById(Long roomId) {
        return chatDao.getChatRoomById(roomId);
    }

    @Override
    public Long getOrCreateChatRoom(Long userId1, Long userId2) {
        return chatDao.getOrCreateChatRoom(userId1, userId2);
    }

    @Override
    public List<Message> getMessage(Long chatRoomId) {
        return chatDao.getMessage(chatRoomId);
    }

    
    @Override
    public void sendMessage(Long chatRoomId, Long senderId, String messageText) {
        // Retrieve the chat room by its ID
        ChatRoom chatRoom = chatDao.getChatRoomById(chatRoomId);

        // Check if the chat room exists
        if (chatRoom == null) {
            throw new ChatRoomNotFoundException("Chat room with ID " + chatRoomId + " not found.");
        }
        // Create a new message entity
        Message message = new Message();
        message.setChatRoom(chatRoom);
        message.setSenderId(senderId);
        message.setMessageText(messageText);
        message.setSentAt(new Date()); // You can set the sent time here

        // Save the message using the DAO
        chatDao.saveMessage(message);
    }
    
@Override
    public User getFriendByChatRoomAndSender(Long chatRoomId, Long senderId) {
        return chatDao.getFriendByChatRoomAndSender(chatRoomId, senderId);
    }
    
    
}
