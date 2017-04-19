package com.federal.controllers;


import org.springframework.stereotype.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sagar on 4/18/17.
 */

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String adminHome(){
        return "admin/dashboard";
    }
}
