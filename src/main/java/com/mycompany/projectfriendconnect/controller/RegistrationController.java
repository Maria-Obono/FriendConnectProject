/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.controller;

import com.mycompany.projectfriendconnect.SERVICE.UserService;
import com.mycompany.projectfriendconnect.POJO.User;
import com.mycompany.projectfriendconnect.exception.UsernameAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author mariagloriaraquelobono
 */
@Controller
public class RegistrationController {
    
    private final UserService userService;

     @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register.htm", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new User());
        return mav;
    }

    @RequestMapping(value = "/registerProcess.htm", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
           @ModelAttribute("user") User user) {
        ModelAndView mav = new ModelAndView();
        try {
        userService.register(user);
            // Redirect to registerProcess.htm with the user's first name as a parameter
            mav.setViewName("redirect:/registerProcess.htm?firstname=" + user.getFirstname());
        } catch (UsernameAlreadyExistsException e) {
            mav.setViewName("register");
            mav.addObject("user", user);
            mav.addObject("errorMessage", "Username already exists. Please choose a different username.");
        }
        return mav;
    }
  
    @RequestMapping(value = "/registerProcess.htm", method = RequestMethod.GET)
    public ModelAndView registerProcessPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("registerProcess");
        // Get the user's first name from the request parameter
        String firstname = request.getParameter("firstname");
        mav.addObject("firstname", firstname);
        return mav;
    }
}
