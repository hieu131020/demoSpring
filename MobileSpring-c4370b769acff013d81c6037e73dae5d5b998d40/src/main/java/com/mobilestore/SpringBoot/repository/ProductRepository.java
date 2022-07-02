package com.mobilestore.SpringBoot.repository;

import com.mobilestore.SpringBoot.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    
}
