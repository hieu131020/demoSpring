/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.service;

import com.mobilestore.SpringBoot.entity.OrderDetail;
import com.mobilestore.SpringBoot.repository.OrderDetailRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class OrderDetailService {

    @Autowired
    OrderDetailRepository repo;

    public void save(OrderDetail detail) {
        repo.save(detail);
    }
    
//    public List<OrderDetail> findByIdOrder(int id){
//        return repo.findByIdOrder(id);
//    }

}
