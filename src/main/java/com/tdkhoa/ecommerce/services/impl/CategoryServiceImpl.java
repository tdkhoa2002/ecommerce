/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.repositories.CategoryRepository;
import com.tdkhoa.ecommerce.services.BannerService;
import com.tdkhoa.ecommerce.services.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository cRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Category> getListCategories() {
        List<Category> cates = this.cRepo.findAll();
        return cates;
    }

    @Override
    public Category add(Map<String, String> params, MultipartFile imageUrl) {
        Category cate = new Category();
        cate.setName(params.get("name"));
        if(params.get("category_id") == null) {
            cate.setCategoryId(null);
        }
        else {
            Category subCate = cRepo.findById(Integer.parseInt(params.get("category_id"))).get();
            cate.setCategoryId(subCate);
        }
        if (!imageUrl.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                cate.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.cRepo.save(cate);
        return cate;
    }

    @Override
    public Category update(Map<String, String> params, @PathVariable(value = "id") int id, MultipartFile imageUrl) {
        Category c = this.cRepo.findById(id).get();
        c.setName(params.get("name"));
        if(params.get("category_id") == null) {
            c.setCategoryId(null);
        }
        else {
            Category subCate = cRepo.findById(Integer.parseInt(params.get("category_id"))).get();
            c.setCategoryId(subCate);
        }
        if (!imageUrl.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                c.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.cRepo.save(c);
        return c;
    }

    @Override
    public Category delete(int id) {
        this.cRepo.delete(this.cRepo.findById(id).get());
        return null;
    }
}
