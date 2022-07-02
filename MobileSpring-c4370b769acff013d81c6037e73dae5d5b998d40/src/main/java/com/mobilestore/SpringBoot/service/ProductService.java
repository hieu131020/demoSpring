/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.service;

import com.mobilestore.SpringBoot.entity.OrderDetail;
import com.mobilestore.SpringBoot.entity.Product;
import com.mobilestore.SpringBoot.repository.OrderDetailRepository;
import com.mobilestore.SpringBoot.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

    @Autowired
    OrderDetailRepository orderDetailRepo;
    
    public Product save(Product product){
        return repo.save(product);
    }

    public Page<Product> listAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return repo.findAll(pageable);
    }

    public Product getProduct(Integer id) {
        return repo.findById(id).get();
    }

    public List<OrderDetail> findAllDetail() {
        return orderDetailRepo.findAll();
    }

    public int sumQuantity(int idProduct) {
        int sum = 0;
        List<OrderDetail> listDetail = findAllDetail();
        for (OrderDetail detail : listDetail) {
            if (detail.getIdProduct() == idProduct) {
                sum = sum + detail.getQuantity();
            }
        }
        return sum;
    }

    public int getQuantityProduct(int idProduct) {
        Product product = getProduct(idProduct);
        return product.getUnit();
    }

    public boolean checkQuantity(Map<Integer, Product> list, int idProduct, int quantityProduct, int quantityInput) {
        boolean check = true;
        int v = 0;
        for (Map.Entry<Integer, Product> entry : list.entrySet()) {
            Product product = entry.getValue();
            if (idProduct == product.getId()) {
                v += product.getUnit();
            }
        }
        if ((quantityProduct - v) < quantityInput) {
            check = false;
        }
        return check;
    }
}
