/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.repositories;

import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Shop;
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
public interface OrderDetailsRepository extends JpaRepository<Orderdetail, Integer> {
    @Query("SELECT od FROM Orderdetail od WHERE od.shopId = ?1 ORDER BY createTime DESC")
    List<Orderdetail> getListOrderdetailByShopId(Shop shop);
    
    @Query("SELECT od FROM Orderdetail od WHERE od.orderId = ?1 ORDER BY createTime DESC")
    List<Orderdetail> getOrderdetailByOrderId(Order1 o);
    
    @Query("SELECT od FROM Orderdetail od WHERE od.shopId = ?1 AND od.status = ?2 ORDER BY createTime DESC")
    List<Orderdetail> getListOrderdetailByShopIdFilStatus(Shop shop, int status);
    
    @Query("SELECT od FROM Orderdetail od WHERE od.orderId = ?1 AND od.status = ?2  ORDER BY createTime DESC")
    List<Orderdetail> getOrderdetailByOrderIdFilStatus(Order1 o, int status);
    
    @Query("SELECT od FROM Orderdetail od WHERE MONTH(od.createTime) = ?2 AND YEAR(od.createTime) = ?3 AND (od.shopId = ?1)")
    List<Orderdetail> findByMonthAndYear(Shop s, int month, int year);
}
