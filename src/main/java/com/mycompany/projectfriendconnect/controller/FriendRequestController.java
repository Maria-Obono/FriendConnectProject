/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.controller;

import com.mycompany.projectfriendconnect.POJO.FriendRequest;
import com.mycompany.projectfriendconnect.POJO.User;

import com.mycompany.projectfriendconnect.SERVICE.UserService;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mariagloriaraquelobono
 */
@Controller
public class FriendRequestController {

    @Autowired
    private UserService userService;

    @GetMapping("/friendRequests.htm")
    public ModelAndView showFriendRequests(HttpSession session) {
        ModelAndView mav = new ModelAndView("friend_requests");

        // Retrieve current user from session
        User currentUser = (User) session.getAttribute("user");

        // Retrieve friend requests for the current user
        List<FriendRequest> friendRequests = userService.getFriendRequestsForCurrentUser(currentUser);

        // Filter out friend requests that have already been accepted by one of the users
        friendRequests = friendRequests.stream()
                .filter(request -> !userService.areFriends(request.getSender().getId(), request.getRecipient().getId()))
                .collect(Collectors.toList());

        // Check if there are no new friend requests
        if (friendRequests.isEmpty()) {
            mav.addObject("noRequestsMessage", "No new friend requests at the moment.");
        } else {
            // Pass friend requests to the view
            mav.addObject("friendRequests", friendRequests);
        }

        return mav;
    }

    @PostMapping("/acceptFriendRequest.htm")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam("requestId") Long requestId) {
        boolean accepted = userService.acceptFriendRequest(requestId);
        if (accepted) {
            return ResponseEntity.ok("Friend request accepted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to accept friend request.");
        }
    }

    @PostMapping("/rejectFriendRequest.htm")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam("requestId") Long requestId) {
        boolean success = userService.rejectFriendRequest(requestId);
        if (success) {
            return ResponseEntity.ok("Friend request rejected successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to reject friend request.");
        }
    }

    @RequestMapping(value = "/findFriends.htm", method = RequestMethod.GET)
    public ModelAndView showFindFriends(HttpServletRequest request) {

        // Retrieve all users except the logged-in user and those already sent requests to
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // Retrieve all users except the logged-in user
        List<User> allUsers = userService.getAllUsersExcept(currentUser.getId());

        // Remove users who have pending requests from or to the current user
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> !userService.hasPendingRequest(currentUser.getId(), user.getId())
                && !userService.hasAcceptedRequest(currentUser.getId(), user.getId())
                && !userService.hasPendingRequest(user.getId(), currentUser.getId())
                && !userService.hasAcceptedRequest(user.getId(), currentUser.getId()))
                .collect(Collectors.toList());

        ModelAndView mav = new ModelAndView("findFriends");
        mav.addObject("users", filteredUsers);
        mav.addObject("currentUser", currentUser);

        return mav;
    }

    // Method to send a friend request to another user
    @ResponseBody
    @RequestMapping(value = "/sendFriendRequest.htm", method = RequestMethod.POST)
    public ResponseEntity<String> sendFriendRequest(@ModelAttribute("recipientId") Long recipientId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sender = (User) session.getAttribute("user");

        if (sender != null) {
            // Check if the users are already friends
            if (userService.areFriends(sender.getId(), recipientId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Already friends"); // Return status indicating already friends
            } // Check if a request has been sent by the other user already
            else if (userService.hasPendingRequest(sender.getId(), recipientId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Pending request already exists"); // Return status indicating pending request already exists
            } // Check if the current user has already accepted a request from the other user
            else if (userService.hasAcceptedRequest(recipientId, sender.getId())) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body("Friend request already accepted"); // Return status indicating request already accepted
            } // If none of the above conditions are met, send a friend request
            else {
                userService.sendFriendRequest(sender.getId(), recipientId);

                return ResponseEntity.ok("Sent"); // Return sent status
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Failed to send friend request."); // Return error message
        }
    }

    @GetMapping("/friendList.htm")
    public ModelAndView viewFriendList(HttpSession session) {
        // Retrieve current user from session
        User currentUser = (User) session.getAttribute("user");

        // Retrieve friend list for the current user
        List<User> friendList = userService.getAllFriends(currentUser.getId());

        // Pass friend list to the view
        ModelAndView mav = new ModelAndView("friendList");
        mav.addObject("friendList", friendList);
        return mav;
    }

@GetMapping("/deleteFriend/{friendId}.htm")
public ModelAndView deleteFriend(HttpSession session, @PathVariable Long friendId) {
    // Retrieve current user from session
    User currentUser = (User) session.getAttribute("user");
    
    // Delete friend
    userService.deleteFriend(currentUser.getId(), friendId);
    
    // Redirect back to friend list
    return new ModelAndView("redirect:/friendList.htm");
}


    
}
