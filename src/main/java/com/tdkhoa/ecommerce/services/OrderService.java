/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.DTO.CartDTO;
import com.tdkhoa.ecommerce.DTO.OrderDTO;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Khoa Tran
 */
public interface OrderService {
    Boolean addOrder(CartDTO cart, User user);
    Set<OrderDTO> convertToDTO(Set<Order1> listOrders);
}
