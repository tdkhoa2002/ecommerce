/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.Pojo.Category;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Khoa Tran
 */
public interface CategoryService {
    List<Category> getListCategories();
    Category add(Map<String, String> params, MultipartFile imageUrl);
    Category update(Map<String, String> params, @PathVariable(value = "id") int id, MultipartFile imageUrl);
    Category delete(int id);
}
