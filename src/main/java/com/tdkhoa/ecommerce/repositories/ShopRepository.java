/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.repositories;

import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
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
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    @Query("SELECT s FROM Shop s WHERE s.userId = ?1")
     Shop findShopByUserId(User id);
}
