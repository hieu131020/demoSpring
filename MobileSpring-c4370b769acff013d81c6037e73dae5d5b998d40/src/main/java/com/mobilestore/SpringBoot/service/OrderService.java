/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.service;

import com.mobilestore.SpringBoot.entity.Order;
import com.mobilestore.SpringBoot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class OrderService {

    @Autowired
    OrderRepository repo;

    public int getIdOrder() {
        return repo.getIdOrder();
    }
    
    public void save(Order order){
        repo.save(order);
    }

}
