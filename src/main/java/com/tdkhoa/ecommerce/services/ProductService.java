/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
public interface ProductService {
    List<Product> getListProducts(User user);
    boolean add(Map<String, String> params, MultipartFile thumbnail, User user, List<MultipartFile> file);
    boolean update(Map<String, String> params, MultipartFile thumbnail, @PathVariable(value = "id") int id, List<MultipartFile> file, User user);
    boolean delete(int id, User user);
}
