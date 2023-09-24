/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.CartDTO;
import com.tdkhoa.ecommerce.DTO.ProductDTO;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Payment;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.Pojo.Voucher;
import com.tdkhoa.ecommerce.repositories.OrderDetailsRepository;
import com.tdkhoa.ecommerce.repositories.OrderRepository;
import com.tdkhoa.ecommerce.repositories.PaymentRepository;
import com.tdkhoa.ecommerce.repositories.ProductRepository;
import com.tdkhoa.ecommerce.repositories.VoucherRepository;
import com.tdkhoa.ecommerce.services.OrderService;
import com.tdkhoa.ecommerce.services.ProductService;
import com.tdkhoa.ecommerce.services.ShopService;
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
    private VoucherRepository voucherRepo;
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private ProductService pServ;
    @Autowired
    private HttpSession s;

    @Override
    public List<Order1> getListOrders() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Order1 addOrder(CartDTO cart, User user) {
        try {
            Order1 order = new Order1();
            order.setCreatedTime(new Date());
            order.setStatus(0);
            order.setUserId(user);
            this.oRepo.save(order);
            double amount = 0;
            Map<Integer, ProductDTO> products = cart.getInfoProduct();
            System.out.println(products);
            for (ProductDTO pDTO : products.values()) {
                Orderdetail d = new Orderdetail();
                d.setQuantity(pDTO.getQty());
                Product product = this.pServ.getProductById(pDTO.getProductId());
                d.setProductId(product);
                d.setOrderId(order);
                Shop shop = product.getShopId();
                d.setShopId(shop);
                amount += product.getPrice() * pDTO.getQty();
                product.setQty(product.getQty() - pDTO.getQty());
                this.odRepo.save(d);
                this.productRepo.save(product);
            }
            Voucher voucher = this.voucherRepo.findById(cart.getVoucher().getId()).get();
            order.setVoucherId(voucher);
            order.setPaymentId(cart.getPayment());
            voucher.setQuantity(voucher.getQuantity() - 1);
            this.voucherRepo.save(voucher);
            order.setTotalAmount(amount - amount * voucher.getValue() / 100);
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