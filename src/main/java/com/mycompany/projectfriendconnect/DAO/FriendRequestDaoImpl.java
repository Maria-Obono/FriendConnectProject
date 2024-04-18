/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.DAO;

import com.mycompany.projectfriendconnect.POJO.FriendRequest;
import com.mycompany.projectfriendconnect.POJO.FriendRequestStatus;
import com.mycompany.projectfriendconnect.POJO.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mariagloriaraquelobono
 */
@Repository
@Transactional
public class FriendRequestDaoImpl implements FriendRequestDao {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FriendRequest findById(Long id) {
        return entityManager.find(FriendRequest.class, id);
    }

    @Transactional
    @Override
    public void update(FriendRequest friendRequest) {
        entityManager.merge(friendRequest);
    }
    
    @Transactional
    @Override
    public List<FriendRequest> getFriendRequestsForUser(User user) {
        String query = "SELECT fr FROM FriendRequest fr WHERE fr.recipient = :user AND fr.status = :status";
        return entityManager.createQuery(query, FriendRequest.class)
                            .setParameter("user", user)
                            .setParameter("status", FriendRequestStatus.PENDING)
                            .getResultList();
    }

  
    @Override
    public List<FriendRequest> getSentRequests(Long userId) {
        String query = "SELECT fr FROM FriendRequest fr WHERE fr.sender.id = :userId";
        return entityManager.createQuery(query, FriendRequest.class)
                            .setParameter("userId", userId)
                            .getResultList();
    }
    
   
    
   @Override
    public boolean checkFriendRequestExists(Long senderId, Long recipientId) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(fr) FROM FriendRequest fr " +
                        "WHERE fr.sender.userId = :senderId " +
                        "AND fr.recipient.userId = :recipientId " +
                        "AND fr.status = :status", Long.class)
                .setParameter("senderId", senderId)
                .setParameter("recipientId", recipientId)
                .setParameter("status", FriendRequestStatus.ACCEPTED)
                .getSingleResult();
        return count > 0;
    }
    
  }
