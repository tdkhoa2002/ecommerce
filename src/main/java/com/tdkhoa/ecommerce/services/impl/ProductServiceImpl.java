/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.repositories.ProductRepository;
import com.tdkhoa.ecommerce.services.ProductService;
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
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository pRepo;
    @Autowired
    private Cloudinary cloudinary;
    
    public List<Product> getListProducts() {
        List<Product> products = this.pRepo.findAll();
        return products;
    }
    
    @Override
    public boolean add(Map<String, String> params, MultipartFile thumbnail, List<MultipartFile> imageUrl, Shop shop, Category cate) {
        Product p = new Product();
        p.setName(params.get("name"));
        p.setDescription(params.get("description"));
        p.setCategoryId(cate);
        p.setShopId(shop);
        if (!imageUrl.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(thumbnail.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                p.setThumbnail(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.pRepo.save(p);
        return true;
    }
    
    @Override
    public boolean update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id) {
        Product p = this.pRepo.findById(id).get();
//        if (!imageUrl.isEmpty()) {
//            try {
//                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//                p.setImageUrl(res.get("secure_url").toString());
//            } catch (IOException ex) {
//                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        this.pRepo.save(p);
        return true;
    }
    
    @Override
    public boolean delete(int id) {
        Product p = this.pRepo.findById(id).get();
        p.setIsDeleted(1);
        this.pRepo.save(p);
        return true;
    }
}
