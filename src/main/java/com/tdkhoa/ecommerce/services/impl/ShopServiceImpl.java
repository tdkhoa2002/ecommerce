/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.ShopRepository;
import com.tdkhoa.ecommerce.services.BannerService;
import com.tdkhoa.ecommerce.services.ShopService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    public List<Shop> getListShops() {
        List<Shop> shops = this.sRepo.findAll();
        return shops;
    }
    
    @Override
    public boolean add(Map<String, String> params, MultipartFile imageUrl, User u) {
        Shop s = new Shop();
        s.setName(params.get("name"));
        s.setDescription(params.get("description"));
        s.setAddress(params.get("address"));
        s.setStatus(1);
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
        return true;
    }
    
    @Override
    public boolean update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id) {
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
        return true;
    }
    
    @Override
    public boolean delete(int id) {
        Shop s = this.sRepo.findById(id).get();
        s.setStatus(0);
        this.sRepo.save(s);
        return true;
    }

    @Override
    public Shop findShopByUserId(User user) {
        return this.sRepo.findShopByUserId(user);
    }

    @Override
    public Shop getShopById(int id) {
        return this.sRepo.findById(id).get();
    }
    
    
}
