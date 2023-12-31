/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.DTO.ProductDTO;
import com.tdkhoa.ecommerce.DTO.SearchDTO;
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
    //ADMIN GET PRODUCTS
    List<ProductDTO> getListProductFilStatus(Map<String, String> params);
    
    //SHOP AND USER GET PRODUCTS
    List<ProductDTO> getListProductsShop(User user, Map<String, String> params);
    List<ProductDTO> getListProducts();
    SearchDTO search(Map<String, String> params);
    Product add(Map<String, String> params, MultipartFile thumbnail, User user, List<MultipartFile> file);
    Product update(Map<String, String> params, MultipartFile thumbnail, @PathVariable(value = "id") int id, List<MultipartFile> file, User user);
    Product delete(int id, User user);
    ProductDTO getProductDTOById(@PathVariable(value = "id") int id);
    Product getProductById(@PathVariable(value = "id") int id);
    ProductDTO convertToDTO(Product p);
    boolean changeStatusProduct(Map<String, String> params);
}
