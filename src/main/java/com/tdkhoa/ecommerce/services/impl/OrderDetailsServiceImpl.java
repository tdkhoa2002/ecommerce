/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.OrderdetailDTO;
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
            OrderdetailDTO orderDetailDTO = OrderdetailDTO.builder()
                    .product(od.getProductId())
                    .order(od.getOrderId())
                    .shop(od.getShopId())
                    .qty(od.getQuantity())
                    .createTime(od.getCreateTime())
                    .status(od.getStatus())
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
