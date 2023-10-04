/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.DTO.OrderDTO;
import com.tdkhoa.ecommerce.DTO.OrderdetailDTO;
import com.tdkhoa.ecommerce.DTO.ShopDTO;
import com.tdkhoa.ecommerce.DTO.UserDTO;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.ShopRepository;
import com.tdkhoa.ecommerce.services.BannerService;
import com.tdkhoa.ecommerce.services.ShopService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository sRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<ShopDTO> getListShops() {
        List<Shop> shops = this.sRepo.findAll();
        List<ShopDTO> shopsDTO = new ArrayList<>();
        shops.forEach(s -> {
            shopsDTO.add(this.toShopDTO(s));
        });
        return shopsDTO;
    }

    @Override
    public Shop add(Map<String, String> params, MultipartFile imageUrl, User u) {
        Shop shop = this.sRepo.findShopByUserId(u);
        if (shop == null) {
            Shop s = new Shop();
            s.setName(params.get("name"));
            s.setDescription(params.get("description"));
            s.setAddress(params.get("address"));
            s.setStatus(0);
            s.setUserId(u);
            if (!imageUrl.isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    s.setImageUrl(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.sRepo.save(s);
            return s;
        }
        return null; //Message error "1 User chi duoc dang ky 1 cua hang"
    }

    @Override
    public Shop update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id) {
        Shop s = this.sRepo.findById(id).get();
        s.setName(params.get("name"));
        s.setDescription(params.get("description"));
        s.setAddress(params.get("address"));
        s.setStatus(1);
        if (!imageUrl.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                s.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.sRepo.save(s);
        return s;
    }

    @Override
    public Shop delete(int id) {
        Shop s = this.sRepo.findById(id).get();
        s.setStatus(0);
        this.sRepo.save(s);
        return s;
    }

    @Override
    public Shop findShopByUserId(User user) {
        return this.sRepo.findShopByUserId(user);
    }

    @Override
    public Shop getShopById(int id) {
        return this.sRepo.findById(id).get();
    }

    @Override
    public Shop activeShop(int id, User user) {
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            Shop shop = this.sRepo.findById(id).get();
            shop.setStatus(1);
            this.sRepo.save(shop);
            return shop;
        }
        return null;
    }

    @Override
    public ShopDTO toShopDTO(Shop shop) {
        UserDTO userDTO = UserDTO.builder()
                .id(shop.getUserId().getId())
                .username(shop.getUserId().getUsername())
                .password(shop.getUserId().getPassword())
                .email(shop.getUserId().getEmail())
                .phone(shop.getUserId().getPhone())
                .build();

        Set<Orderdetail> listOrderdetails = shop.getOrderdetailSet();
        Set<OrderdetailDTO> listOrderdetailsDTO = new HashSet<>();
        for (Orderdetail od : listOrderdetails) {
            OrderdetailDTO orderdetailDTO = OrderdetailDTO.builder()
                    .id(od.getId())
                    .qty(od.getQuantity())
                    .createTime(od.getCreateTime())
                    .status(od.getStatus())
//                    .product(product)
                    .build();
            listOrderdetails.add(od);
        }

        ShopDTO shopDTO = ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .description(shop.getDescription())
                .address(shop.getAddress())
                .imageUrl(shop.getImageUrl())
                .status(shop.getStatus())
                .userDTO(userDTO)
                .build();
        return shopDTO;
    }

    @Override
    public ShopDTO viewManageShop(User user) {
        Shop shop = this.sRepo.findShopByUserId(user);
        ShopDTO shopDTO = this.toShopDTO(shop);
        return shopDTO;
    }

    @Override
    public Boolean checkShop(User user) {
        Shop s = this.sRepo.findShopByUserId(user);
        if (s == null) {
            return false;
        }
        return true;
    }
}
