/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.CartDTO;
import com.tdkhoa.ecommerce.DTO.DateDTO;
import com.tdkhoa.ecommerce.DTO.OrderDTO;
import com.tdkhoa.ecommerce.DTO.ProductDTO;
import com.tdkhoa.ecommerce.DTO.StatDTO;
import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Payment;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.Pojo.Voucher;
import com.tdkhoa.ecommerce.repositories.AddressRepository;
import com.tdkhoa.ecommerce.repositories.OrderDetailsRepository;
import com.tdkhoa.ecommerce.repositories.OrderRepository;
import com.tdkhoa.ecommerce.repositories.PaymentRepository;
import com.tdkhoa.ecommerce.repositories.ProductRepository;
import com.tdkhoa.ecommerce.repositories.VoucherRepository;
import com.tdkhoa.ecommerce.services.EmailService;
import com.tdkhoa.ecommerce.services.OrderDetailService;
import com.tdkhoa.ecommerce.services.OrderService;
import com.tdkhoa.ecommerce.services.PaymentService;
import com.tdkhoa.ecommerce.services.ProductService;
import com.tdkhoa.ecommerce.services.ShopService;
import com.tdkhoa.ecommerce.services.UserService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private ProductRepository productRepo;
    @Autowired
    private AddressRepository addressRepo;
    @Autowired
    private ProductService pServ;
    @Autowired
    private UserService uServ;
    @Autowired
    private EmailService eServ;
    @Autowired
    private OrderDetailService odServ;
    @Autowired
    private HttpSession s;

    @Override
    public Boolean addOrder(CartDTO cart, User user) {
        try {
            Order1 order = new Order1();
            order.setCreatedTime(new Date());
            order.setUserId(user);
            this.oRepo.save(order);
            double amount = 0;
            List<ProductDTO> products = cart.getInfoProduct();
            System.out.println(products);
            for (ProductDTO pDTO : products) {
                Orderdetail d = new Orderdetail();
                d.setQuantity(pDTO.getQuantity());
                Product product = this.pServ.getProductById(pDTO.getId());
                d.setProductId(product);
                d.setOrderId(order);
                Address address = this.addressRepo.findById(cart.getAddress()).get();
                d.setAddressId(address);
                d.setStatus(0);
                product.setSold(product.getSold() + pDTO.getQuantity());
                d.setCreateTime(new Date());
                Shop shop = product.getShopId();
                d.setShopId(shop);
                amount += product.getPrice() * pDTO.getQuantity();
                product.setQty(product.getQty() - pDTO.getQuantity());
                this.eServ.sendMailOrder(shop);
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
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Set<OrderDTO> convertToDTO(Set<Order1> listOrders) {
        Set<OrderDTO> listOrdersDTO = new HashSet<>();
        for (Order1 o : listOrders) {
            OrderDTO oDTO = OrderDTO.builder()
                .id(o.getId())
                .total_amount(o.getTotalAmount())
                .createdTime(o.getCreatedTime())
                .payment(o.getPaymentId())
                .user(this.uServ.convertToDTO(o.getUserId()))
                .voucher(o.getVoucherId())
                .build();
            listOrdersDTO.add(oDTO);
        }

        return listOrdersDTO;
    }

    @Override
    public Object findOrderByStore(Shop shop, DateDTO dateDTO) {
        List<StatDTO> listDto = new ArrayList<>();
        List<Orderdetail> listOrderDetail = this.odServ.findByDate(shop, dateDTO.getMonth(), dateDTO.getYear());
        System.out.println(listOrderDetail);
        if (listOrderDetail != null) {
            for (Orderdetail d : listOrderDetail) {
                StatDTO dto = StatDTO.builder()
                        .date(d.getCreateTime())
                        .total(d.getProductId().getPrice() * d.getQuantity())
                        .name(d.getProductId().getName())
                        .build();
                listDto.add(dto);
            }

        }

        return listDto;
    }
}