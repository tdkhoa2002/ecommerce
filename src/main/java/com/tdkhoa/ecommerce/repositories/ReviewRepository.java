/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.repositories;

import com.tdkhoa.ecommerce.DTO.StarDTO;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Review;
import java.util.List;
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
public interface ReviewRepository extends JpaRepository<Review, Integer>{
    @Query("SELECT NEW com.tdkhoa.ecommerce.DTO.StarDTO(r.star, COUNT(r.star)) FROM Review r WHERE r.productId = ?1 GROUP BY r.star ORDER BY star DESC")
    List<StarDTO> countStarProduct(Product p);
    
    @Query("SELECT r FROM Review r WHERE r.productId = ?1")
     List<Review> findReviewsByProductId(Product p);
}
