/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.repositories.CategoryRepository;
import com.tdkhoa.ecommerce.services.CategoryService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Khoa Tran
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository cRepo;
    
    @Override
    public List<Category> getListCategories() {
        List<Category> cates = this.cRepo.findAll();
        return cates;
    }
    
    @Override
    public boolean add(Map<String, String> params) {
        Category cate = new Category();
        cate.setName(params.get("name"));
        this.cRepo.save(cate);
        return true;
    }
    
    @Override
    public boolean update(Map<String, String> params, @PathVariable(value = "id") int id) {
        Category c = this.cRepo.findById(id).get();
        c.setName(params.get("name"));
        this.cRepo.save(c);
        return true;
    }
    
    @Override
    public boolean delete(int id) {
        this.cRepo.delete(this.cRepo.findById(id).get());
        return true;
    }
}
