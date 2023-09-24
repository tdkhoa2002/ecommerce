/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.DTO.CartDTO;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Khoa Tran
 */
public interface OrderService {
    List<Order1> getListOrders();
    Order1 addOrder(CartDTO cart, User user);
    Order1 deleteOrder(int id);
}
