/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.controller;


import com.mycompany.projectfriendconnect.POJO.Message;
import com.mycompany.projectfriendconnect.POJO.User;
import com.mycompany.projectfriendconnect.SERVICE.ChatService;
import com.mycompany.projectfriendconnect.SERVICE.UserService;

import java.util.List;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mariagloriaraquelobono
 */

@Controller
public class ChatController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/friendsChat.htm")
    public ModelAndView showFriendChat(HttpSession session) {
        ModelAndView mav = new ModelAndView("friend_chat");

        // Retrieve current user from session
        User currentUser = (User) session.getAttribute("user");

        // Retrieve friends of the current user
        List<User> friends = userService.getAllFriends(currentUser.getId());

        mav.addObject("friends", friends);
        return mav;
    }

    @GetMapping("/chat/{friendId}.htm")
    public ModelAndView openChatRoom(@PathVariable("friendId") Long friendId, HttpSession session) {
        ModelAndView mav = new ModelAndView("chat_room");

        // Retrieve current user from session
        User currentUser = (User) session.getAttribute("user");

        // Retrieve friend user
        User friend = userService.getUserById(friendId);

        // Create or retrieve chat room for this pair of users
        Long chatRoomId = chatService.getOrCreateChatRoom(currentUser.getId(), friendId);

        // Retrieve messages for this chat room
        List<Message> messages = chatService.getMessage(chatRoomId);

        mav.addObject("friend", friend);
        mav.addObject("messages", messages);
        mav.addObject("chatRoomId", chatRoomId);
        mav.addObject("senderId", currentUser.getId());
        return mav;
    }
    
    @PostMapping("/sendMessage.htm")
    public ModelAndView sendMessage(@RequestParam("chatRoomId") Long chatRoomId,
                                     @RequestParam("senderId") Long senderId,
                                     @RequestParam("message") String messageText,
                                     HttpSession session) {
    
        // Retrieve the friend's information based on the chatRoomId and senderId
        User friend = chatService.getFriendByChatRoomAndSender(chatRoomId, senderId);
        
        chatService.sendMessage(chatRoomId, senderId, messageText);

        // After saving the message, retrieve updated messages for the chat room
        List<Message> updatedMessages = chatService.getMessage(chatRoomId);

        // Prepare the ModelAndView with the updated messages and return it
        ModelAndView mav = new ModelAndView("chat_room");
        
        mav.addObject("messages", updatedMessages);
        mav.addObject("chatRoomId", chatRoomId);
        mav.addObject("senderId", senderId);
        mav.addObject("friend", friend);
        return mav;
    }

    
    @GetMapping("/getMessages.htm")
    @ResponseBody
    public List<Message> getMessages(@RequestParam("chatRoomId") Long chatRoomId) {
    return chatService.getMessage(chatRoomId);
}

}


