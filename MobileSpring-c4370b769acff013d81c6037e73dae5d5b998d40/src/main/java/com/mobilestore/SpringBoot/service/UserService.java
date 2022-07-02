/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.service;

import com.mobilestore.SpringBoot.entity.User;
import com.mobilestore.SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public User getUserByUsername(String username) {
        User user = repo.findUserByUsername(username);
        return user;
    }
}
