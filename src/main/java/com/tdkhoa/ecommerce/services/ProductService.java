/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Banner;
import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.repositories.ProductRepository;
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
public interface ProductService {
    List<Product> getListProducts();
    boolean add(Map<String, String> params, MultipartFile thumbnail, List<MultipartFile> imageUrl, Shop shop, Category cate);
    boolean update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id);
    boolean delete(int id);
}
