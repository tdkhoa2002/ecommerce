/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.repositories;

import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Khoa Tran
 */
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.shopId = ?1")
     List<Product> findProductByShopId(Shop shop);
     
     List<Product> findByOrderBySoldDesc();
     
     @Query("SELECT p FROM Product p WHERE p.shopId.status = 1 AND (p.name LIKE %?1%" 
             + " OR p.categoryId.name LIKE %?1%"
             + " OR p.shopId.name LIKE %?1%"
             + " OR concat(p.price, ' ') LIKE %?1%)")
     List<Product> search(String keyword);
     
     @Query("SELECT p FROM Product p WHERE p.status = ?1")
     List<Product> findProducsFilStatus(int status);
}
