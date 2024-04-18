/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.POJO;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import javax.persistence.*;
import org.springframework.web.multipart.MultipartFile;



/**
 *
 * @author mariagloriaraquelobono
 */

@Entity
@Table(name = "users")
public class User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId; 

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;
    
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;
    
    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> sentRequests = new HashSet<>();
    
    @OneToMany(mappedBy = "recipient")
    private Set<FriendRequest> receivedRequests = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "friend_requests",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friends = new ArrayList<>();
    

    @Column(name = "profile_picture_path")
    private String profilePicturePath; // This attribute stores the path of the profile picture
    
    @Transient
   private MultipartFile photo;
   

    // Getters and setters for all fields
    
    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }

  
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    // Getter and setter for sentRequests
    public Set<FriendRequest> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(Set<FriendRequest> sentRequests) {
        this.sentRequests = sentRequests;
    }
    
    public Set<FriendRequest> getReceivedRequests() {
        return receivedRequests;
    }

    public void setReceivedRequests(Set<FriendRequest> receivedRequests) {
        this.receivedRequests = receivedRequests;
    }

   public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
    
    // Method to check if a user is already a friend
    public boolean isFriend(User user) {
        //return this.friends.contains(user);
        for (User friend : friends) {
        if (friend.equals(user)) {
            return true;
        }
    }
    return false;
    }

    // Method to check if a friend request has been sent to a user
    public boolean hasReceivedFriendRequest(User user) {
        for (FriendRequest request : this.receivedRequests) {
            if (request.getSender().equals(user)) {
                return true;
            }
        }
        return false;
    }
    
    // Method to check if a friend request has been sent by the user
    public boolean hasSentFriendRequest(User user) {
        for (FriendRequest request : this.sentRequests) {
            if (request.getRecipient().equals(user)) {
                return true;
            }
        }
        return false;
    }
    
    //public String getStatus() {
    //    return status;
   // }

    //public void setStatus(String status) {
    //    this.status = status;
    //}
    
    public String getProfilePicturePath() {
        return profilePicturePath;
    }

  
    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

   public String getImageName() {
        if (this.profilePicturePath != null) {
            String[] parts = this.profilePicturePath.split("/");
            return parts[parts.length - 1];
        }
        return null;
    }

   
    
    
    
     
}