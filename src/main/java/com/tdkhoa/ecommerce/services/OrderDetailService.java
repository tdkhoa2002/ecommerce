/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.DTO.OrderdetailDTO;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Khoa Tran
 */
public interface OrderDetailService {
    List<OrderdetailDTO> getListOrderDetailsShop(int shopId);
    boolean changeStatusOrderDetail(int id, Map<String, String> params);
    List<OrderdetailDTO> getListOrderDetailsUser(User u);
    OrderdetailDTO toOrderdetailDTO(Orderdetail od);
}
