/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.controller;

import com.mobilestore.SpringBoot.security.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ACER
 */
@Controller
public class UserController {

    private final UserDetailServiceImp service;

    @Autowired
    public UserController(UserDetailServiceImp service) {
        this.service = service;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/403")
    public String handleError(){
        return "403";
    }
    
    
    
   
}
