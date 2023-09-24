/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.CartDTO;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.OrderDetailsRepository;
import com.tdkhoa.ecommerce.repositories.OrderRepository;
import com.tdkhoa.ecommerce.services.OrderService;
import com.tdkhoa.ecommerce.services.ProductService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository oRepo;
    @Autowired
    private OrderDetailsRepository odRepo;
    @Autowired
    private ProductService pServ;
    @Autowired
    private HttpSession s;

    @Override
    public List<Order1> getListOrders() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Order1 addOrder(Map<String, CartDTO> carts, User user) {
        try {

            Order1 order = new Order1();
            order.setTimestamp(new Date());
            order.setStatus(0);
            double amount = 0;

            for (CartDTO c : carts.values()) {
                Orderdetail d = new Orderdetail();
                d.setQuantity(c.getCount());
                Product p = this.pServ.getProductById(c.getId());
                d.setProduct(p);
                d.setOrder1(order);
                amount += p.getPrice() * c.getCount();
                this.odRepo.save(d);
            }
            order.setTotalAmount(amount);
            order.setUserId(user);
            this.oRepo.save(order);
            s.setAttribute("cart", null);
            return order;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Order1 deleteOrder(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
