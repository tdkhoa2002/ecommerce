/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.CategoryDTO;
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
import java.util.List;
import java.util.Map;
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
    public List<OrderdetailDTO> getListOrderDetailsShop(User user, Map<String, String> params) {
        Shop shop = this.sServ.findShopByUserId(user);
        List<OrderdetailDTO> listDTO = new ArrayList<>();
        if (params.get("status") != null) {
            int status = Integer.parseInt(params.get("status"));
            List<Orderdetail> listOrderDetails = this.odRepo.getListOrderdetailByShopIdFilStatus(shop, status);
            for (Orderdetail od : listOrderDetails) {
                listDTO.add(this.toOrderdetailDTO(od));
            }
        } else {
            List<Orderdetail> listOrderDetails = this.odRepo.getListOrderdetailByShopId(shop);
            for (Orderdetail od : listOrderDetails) {
                listDTO.add(this.toOrderdetailDTO(od));
            }
        }
        return listDTO;
    }

    @Override
    public OrderdetailDTO toOrderdetailDTO(Orderdetail od) {
        if (od == null) {
            return null;
        } else {
            UserDTO userDTO = UserDTO.builder()
                    .id(od.getOrderId().getUserId().getId())
                    .username(od.getOrderId().getUserId().getUsername())
                    .password(od.getOrderId().getUserId().getPassword())
                    .email(od.getOrderId().getUserId().getEmail())
                    .phone(od.getOrderId().getUserId().getPhone())
                    .fullName(od.getOrderId().getUserId().getFullName())
                    .build();
            
            CategoryDTO cateDTO = CategoryDTO.builder()
                    .id(od.getProductId().getCategoryId().getId())
                    .name(od.getProductId().getCategoryId().getName())
                    .build();

            ProductDTO productDTO = ProductDTO.builder()
                    .id(od.getProductId().getId())
                    .name(od.getProductId().getName())
                    .thumbnail(od.getProductId().getThumbnail())
                    .category(cateDTO)
                    .price(od.getProductId().getPrice())
                    .build();

            OrderDTO orderDTO = OrderDTO.builder()
                    .id(od.getOrderId().getId())
                    .total_amount(od.getOrderId().getTotalAmount())
                    .build();

            ShopDTO shopDTO = ShopDTO.builder()
                    .id(od.getShopId().getId())
                    .name(od.getShopId().getName())
                    .build();

            OrderdetailDTO orderDetailDTO = OrderdetailDTO.builder()
                    .id(od.getId())
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
    public List<OrderdetailDTO> getListOrderDetailsUser(User u, Map<String, String> params) {
        Set<Order1> listOrder = u.getOrder1Set();
        List<OrderdetailDTO> listDTO = new ArrayList<>();
        if (params.get("status") != null) {
            int status = Integer.parseInt(params.get("status"));
            for (Order1 o : listOrder) {
                List<Orderdetail> listOD = this.odRepo.getOrderdetailByOrderIdFilStatus(o, status);
                for (Orderdetail od : listOD) {
                    listDTO.add(toOrderdetailDTO(od));
                }
            }
        } else {
            for (Order1 o : listOrder) {
                List<Orderdetail> listOD = this.odRepo.getOrderdetailByOrderId(o);
                for (Orderdetail od : listOD) {
                    listDTO.add(toOrderdetailDTO(od));
                }
            }
        }
        return listDTO;
    }

    @Override
    public boolean changeStatusOrderDetail(int id, Map<String, String> params) {
        Orderdetail od = this.odRepo.findById(id).get();
        od.setStatus(Integer.parseInt(params.get("status")));
        this.odRepo.save(od);
        return true;
    }

    @Override
    public List<Orderdetail> findByDate(Shop s, int month, int year) {
        return this.odRepo.findByMonthAndYear(s, month,year);
    }
}
