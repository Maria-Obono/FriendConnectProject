package com.mycompany.projectfriendconnect.POJO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "friend_requests")
public class FriendRequest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendId")
    private Long friendId;

   
    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;
   
    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User recipient;
    
     @Enumerated(EnumType.STRING)
     @Column(name = "status")
    private FriendRequestStatus status;

     
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
     
     
    // Constructors, getters, and setters
    public FriendRequest() {
    }

    public FriendRequest(User sender, User receiver, FriendRequestStatus status) {
        this.sender = sender;
        this.recipient = receiver;
        this.status = status;
    }

    // Getters and setters
    public Long getId() {
        return friendId;
    }

    public void setId(Long id) {
        this.friendId = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User receiver) {
        this.recipient = receiver;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }
    
 
}