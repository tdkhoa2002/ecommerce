/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Review;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.ShopRepository;
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
public interface ShopService {
    List<Shop> getListShops();
    Shop getShopById(int id);
    Shop add(Map<String, String> params, MultipartFile imageUrl, User u);
    Shop update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id);
    Shop delete(int id);
    Shop findShopByUserId(User user);
    Shop activeShop(@PathVariable(value = "id") int id, User user);
}
