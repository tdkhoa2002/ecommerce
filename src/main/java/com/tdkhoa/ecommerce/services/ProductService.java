/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

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
    List<Product> getListProductsShop(User user);
    List<Product> getListProducts();
    Product add(Map<String, String> params, MultipartFile thumbnail, User user, List<MultipartFile> file);
    Product update(Map<String, String> params, MultipartFile thumbnail, @PathVariable(value = "id") int id, List<MultipartFile> file, User user);
    Product delete(int id, User user);
    Product getProductById(@PathVariable(value = "id") int id);
}
