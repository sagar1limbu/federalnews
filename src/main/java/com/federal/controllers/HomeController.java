package com.federal.controllers;

import com.federal.entities.Roles;
import com.federal.entities.UserRoles;
import com.federal.entities.UserVerficationToken;
import com.federal.entities.Users;
import com.federal.models.Registration;
import com.federal.services.UserSecurityService;
import com.federal.services.UserService;
import com.federal.utility.MailConstructor;
import com.federal.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by sagar on 4/12/17.
 */

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserSecurityService userSecurityService;


    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/index")
    public String home1(){
        return "index";
    }


    @RequestMapping("/login")
    public String loginPage(){
        return "user/login";
    }

    @RequestMapping("/register")
    public String register(){
        return "user/registration";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPost(
            HttpServletRequest request,
            @ModelAttribute Registration registration,
            Model model
            ) throws Exception{
        if(userService.findByUsername(registration.getUserName()) != null){
            model.addAttribute("usernameExists",true);
            return "user/registration";

        }

        if(userService.findByEmail(registration.getEmail()) != null){
            model.addAttribute("emailExists",true);
            return "user/registration";
        }

        Users users = new Users();
        users.setUsername(registration.getUserName());
        users.setEmail(registration.getEmail());
        users.setFirstName(registration.getFirstName());
        users.setLastName(registration.getLastName());
        users.setEnabled(false);

        String password = registration.getPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        users.setPassword(encryptedPassword);

        Roles roles = new Roles();
        roles.setName("USER");
        roles.setRoleId(2);
        Set<UserRoles> userRoles = new HashSet<>();
        userRoles.add(new UserRoles(users,roles));
        userService.createUser(users,userRoles);


        String token = UUID.randomUUID().toString();
        userService.createPasswordVerficationToken(users,token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl,request.getLocale(),token,users,password);
        mailSender.send(email);
        model.addAttribute("emailSent",true);
        return "user/registration";



    }

    @RequestMapping("/newUser")
    public String verify(Locale locale, @RequestParam("token") String token, Model model){

        UserVerficationToken userVerficationToken = userService.getUserVerficationToken(token);

        if (userVerficationToken == null) {
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        Users users = userVerficationToken.getUsers();
        String username = users.getUsername();
        users.setEnabled(true);


        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userService.save(users);

        model.addAttribute("users", users);

        model.addAttribute("classActiveEdit", true);
        return "user/registration";

    }
}

