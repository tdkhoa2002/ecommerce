/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.OrderDTO;
import com.tdkhoa.ecommerce.DTO.OrderdetailDTO;
import com.tdkhoa.ecommerce.DTO.ProductDTO;
import com.tdkhoa.ecommerce.DTO.ShopDTO;
import com.tdkhoa.ecommerce.DTO.UserDTO;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.OrderDetailsRepository;
import com.tdkhoa.ecommerce.services.OrderDetailService;
import com.tdkhoa.ecommerce.services.ShopService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailService {
    @Autowired
    private ShopService sServ;
    @Autowired
    private OrderDetailsRepository odRepo;

    @Override
    public List<OrderdetailDTO> getListOrderDetailsShop(int shopId) {
        Shop shop = this.sServ.getShopById(shopId);
        List<Orderdetail> listOrderDetails = this.odRepo.getListOrderdetailByShopId(shop);
        List<OrderdetailDTO> listDTO = new ArrayList<>();
        for(Orderdetail od: listOrderDetails) {
            listDTO.add(this.toOrderdetailDTO(od));
        }
        return listDTO; 
    }

    @Override
    public OrderdetailDTO toOrderdetailDTO(Orderdetail od) {
        if(od == null) {
            return null;
        }
        else {
            UserDTO userDTO = UserDTO.builder()
                    .id(od.getOrderId().getUserId().getId())
                    .username(od.getOrderId().getUserId().getUsername())
                    .password(od.getOrderId().getUserId().getPassword())
                    .email(od.getOrderId().getUserId().getEmail())
                    .phone(od.getOrderId().getUserId().getPhone())
                    .build();
            
            ProductDTO productDTO = ProductDTO.builder()
                    .id(od.getProductId().getId())
                    .name(od.getProductId().getName())
                    .build();
            
            OrderDTO orderDTO = OrderDTO.builder()
                    .id(od.getOrderId().getId())
                    .build();
            
            ShopDTO shopDTO = ShopDTO.builder()
                    .id(od.getShopId().getId())
                    .name(od.getShopId().getName())
                    .build();
            
            OrderdetailDTO orderDetailDTO = OrderdetailDTO.builder()
                    .product(productDTO)
                    .order(orderDTO)
                    .shop(shopDTO)
                    .qty(od.getQuantity())
                    .createTime(od.getCreateTime())
                    .status(od.getStatus())
                    .user(userDTO)
                    .build();
            return orderDetailDTO;
        }
    }

    @Override
    public List<OrderdetailDTO> getListOrderDetailsUser(User u) {
        Set<Order1> listOrder = u.getOrder1Set();
        List<OrderdetailDTO> listDTO = new ArrayList<>();
        for(Order1 o: listOrder) {
            Orderdetail od = this.odRepo.getOrderdetailByOrderId(o);
            listDTO.add(toOrderdetailDTO(od));
        }
        return listDTO;
    }
    
}
