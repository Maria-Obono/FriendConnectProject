/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectfriendconnect.controller;

import com.mycompany.projectfriendconnect.POJO.Login;
import com.mycompany.projectfriendconnect.POJO.User;
import com.mycompany.projectfriendconnect.SERVICE.UserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mariagloriaraquelobono
 */

@Controller
public class LoginController {

    private final UserService userService;

    private static final String UPLOAD_DIRECTORY = "/Users/mariagloriaraquelobono/FriendConnect-Photos/";

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;

    }

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public ModelAndView showLogin() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("login", new Login());
        return mav;
    }

    @RequestMapping(value = "/loginProcess.htm", method = RequestMethod.POST)
    public ModelAndView loginProcess(@ModelAttribute("login") Login login, HttpServletRequest request) {
        ModelAndView mav;
        User user = userService.validateUser(login);

        if (user != null) {
            mav = new ModelAndView("welcome");
            mav.addObject("firstname", user.getFirstname());

            // Add userId and imageName to the model if profilePicturePath is not null
            if (user.getProfilePicturePath() != null) {
                // Add userId and imageName to the model
                mav.addObject("userId", user.getUserId());
                mav.addObject("imageName", user.getImageName());
            }

            // Add profilePicturePath to the model
            mav.addObject("profilePicturePath", user.getProfilePicturePath());

            // Set user in session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        } else {
            mav = new ModelAndView("login");
            mav.addObject("message", "Username or Password is wrong!!");
        }
        return mav;
    }

    @GetMapping("/welcome.htm")
    public ModelAndView welcomePage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("welcome");

        // Retrieve the user from the session or database
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if the user is not null and has the firstname attribute
        if (user != null && user.getFirstname() != null) {
            mav.addObject("firstname", user.getFirstname());

            // Add userId and imageName to the model if profilePicturePath is not null
            if (user.getProfilePicturePath() != null) {
                // Extract imageName from profilePicturePath
                String[] parts = user.getProfilePicturePath().split("/");
                String imageName = parts[parts.length - 1]; // Get the last part which is the image name
                // Add userId and imageName to the model
                mav.addObject("userId", user.getUserId());
                mav.addObject("imageName", imageName);
            }

            // Add profilePicturePath to the model
            mav.addObject("profilePicturePath", user.getProfilePicturePath());
        }

        return mav;
    }

    @GetMapping("/uploadProfilePicture.htm")
    public ModelAndView showUploadProfilePicture(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("uploadProfilePicture");

        // Retrieve the user from the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if the user is not null and has the firstname attribute
        if (user != null && user.getFirstname() != null) {
            mav.addObject("firstname", user.getFirstname());

            //to add the user's ID for constructing the image URL
            mav.addObject("userId", user.getId());

        // Add the user's profile picture path if available
            if (user.getProfilePicturePath() != null) {
                // Extract the image name from the profile picture path
                String[] parts = user.getProfilePicturePath().split("/");
                String imageName = parts[parts.length - 1];

                // Add the image name to the model
                mav.addObject("imageName", imageName);
            }

        }

        return mav;
    }

    @GetMapping("/pictures/images/{userId}/{imageName:.+}.htm")
    @ResponseBody
    public byte[] getImage(@PathVariable String userId, @PathVariable String imageName, HttpServletRequest request) throws IOException {
        // Get the absolute path to the images directory
        //String webappRoot = request.getSession().getServletContext().getRealPath("/");

        String imagePath = UPLOAD_DIRECTORY + "images/" + userId + "/" + imageName;

        // Read the image file as bytes
        File file = new File(imagePath);
        return Files.readAllBytes(file.toPath());
    }

    // Controller method to handle profile picture upload
    @RequestMapping(value = "/uploadProfilePicture.htm", method = RequestMethod.POST)
    public ModelAndView uploadProfilePicture(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") MultipartFile file) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {

                // Upload the profile picture and get the file path
                String filePath = userService.uploadProfilePicture(user.getId(), file);
                // Update user's profile picture path in the database
                userService.updateProfilePicturePath(user.getId(), filePath);

                // Set profilePicturePath in session attribute for immediate use
                session.setAttribute("profilePicturePath", filePath);

                // Update session attributes with new picture information
                session.setAttribute("userId", user.getId());
                session.setAttribute("imageName", filePath.substring(filePath.lastIndexOf("/") + 1));

                // Update the model attributes with the new picture path
                mav.addObject("firstname", user.getFirstname());

                // Redirect to a success page
                mav.setViewName("welcome");
            } catch (IOException e) {
                mav.setViewName("redirect:/uploadProfilePicture.htm?error=io");
                mav.addObject("errorMessage", "An error occurred while uploading the profile picture.");
            }
        } else {
            mav.setViewName("redirect:/login.htm");
        }
        return mav;
    }

}
