/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.POJO;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author mariagloriaraquelobono
 */
@Entity
@Table(name = "chat_rooms")
public class ChatRoom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chatRoomId")
    private String chatRoomId;
  
    
    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;
    
    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;
    
    
    
    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatRoom() {
        return chatRoomId;
    }

    public void setChatRoom(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

  

    
    
}
