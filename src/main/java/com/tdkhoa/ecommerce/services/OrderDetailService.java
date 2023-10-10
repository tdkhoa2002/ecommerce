/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.DTO.OrderdetailDTO;
import com.tdkhoa.ecommerce.DTO.ProductQuantityDTO;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Khoa Tran
 */
public interface OrderDetailService {
    List<OrderdetailDTO> getListOrderDetailsShop(User user, Map<String, String> params);
    boolean changeStatusOrderDetail(int id, Map<String, String> params);
    List<OrderdetailDTO> getListOrderDetailsUser(User u, Map<String, String> params);
    OrderdetailDTO toOrderdetailDTO(Orderdetail od);
    List<Orderdetail> findByDate(Shop s, int month, int year);
    List<ProductQuantityDTO> groupByProductId(Shop s);
}
