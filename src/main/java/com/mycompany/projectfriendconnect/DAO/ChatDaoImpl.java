/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.DAO;


import com.mycompany.projectfriendconnect.POJO.ChatRoom;
import com.mycompany.projectfriendconnect.POJO.Message;


import com.mycompany.projectfriendconnect.POJO.User;
import java.math.BigInteger;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 *
 * @author mariagloriaraquelobono
 */
@Repository
public class ChatDaoImpl implements ChatDao {
    
    @PersistenceContext
    private EntityManager entityManager;

    

    @Override
    public ChatRoom getChatRoomById(Long chatRoomId) {
        return entityManager.find(ChatRoom.class, chatRoomId);
    }

    
    
    
    
    @Override
    public Long getOrCreateChatRoom(Long userId1, Long userId2) {
        // Check if a chat room already exists for the given user pair
        List<ChatRoom> existingChatRooms = entityManager.createQuery(
        "SELECT cr FROM ChatRoom cr " +
        "WHERE (cr.user1.userId = :userId1 AND cr.user2.userId = :userId2) OR " +
        "(cr.user1.userId = :userId2 AND cr.user2.userId = :userId1)", ChatRoom.class)
        .setParameter("userId1", userId1)
        .setParameter("userId2", userId2)
        .getResultList();

        // If chat room doesn't exist, create a new one
        if (existingChatRooms.isEmpty()) {
            User user1 = entityManager.getReference(User.class, userId1);
            User user2 = entityManager.getReference(User.class, userId2);
            ChatRoom newChatRoom = new ChatRoom();
            newChatRoom.setUser1(user1);
            newChatRoom.setUser2(user2);
            entityManager.persist(newChatRoom);
            return newChatRoom.getId();
        } else {
            // Return the first chat room ID found (assuming there should be only one)
            return existingChatRooms.get(0).getId();
        }
    }

     @Override
    public List<Message> getMessage(Long chatRoomId) {
        return entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.chatRoom.id = :chatRoomId", Message.class)
                .setParameter("chatRoomId", chatRoomId)
                .getResultList();
    }

  @Override
    public void saveMessage(Message message) {
        entityManager.persist(message);
    }
    
    
@Override
    public User getFriendByChatRoomAndSender(Long chatRoomId, Long senderId) {
        // the native SQL query to retrieve the friend's user ID based on the chat room and sender
        String sql = "SELECT CASE WHEN cr.user1_id = :senderId THEN cr.user2_id ELSE cr.user1_id END FROM chat_rooms cr WHERE cr.id = :chatRoomId";
        
        // Execute the native SQL query and retrieve the user ID
        BigInteger friendUserId = (BigInteger) entityManager.createNativeQuery(sql)
            .setParameter("chatRoomId", chatRoomId)
            .setParameter("senderId", senderId)
            .getSingleResult();
        
        // Convert the BigInteger to Long
        Long friendUserIdLong = friendUserId.longValueExact();
        
        // Fetch the user entity corresponding to the retrieved user ID
        return entityManager.find(User.class, friendUserIdLong);
    }
}
