/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.entity;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author ACER
 */
@Data
@AllArgsConstructor
public class Cart {
    private String username;
    private HashMap<Integer, Product> cart;
    
    public void add(Product product){
        if(this.cart == null){
            cart = new HashMap<Integer, Product>();
        }
        int key = product.getId();
        if(this.cart.containsKey(key)){
            int quantity = this.cart.get(key).getUnit();
            product.setUnit(quantity + product.getUnit());
        }
        this.cart.put(key, product);
    }
    
    public void delete(int id){
        if(this.cart == null){
            return;
        }
        if(this.cart.containsKey(id)){
            this.cart.remove(id);
        }
    }
}
