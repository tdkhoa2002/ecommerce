/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.repositories;

import com.tdkhoa.ecommerce.Pojo.Image;
import com.tdkhoa.ecommerce.Pojo.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Khoa Tran
 */
public interface ImageRepository extends JpaRepository<Image, Integer>{
    @Query("SELECT i FROM Image i WHERE i.productId = ?1")
     List<Image> findImageByProductId(Product p);
}
