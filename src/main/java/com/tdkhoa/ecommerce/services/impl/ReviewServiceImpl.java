/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.Pojo.Review;
import com.tdkhoa.ecommerce.repositories.ReviewRepository;
import com.tdkhoa.ecommerce.services.ReviewService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository rRepo;
    
    @Override
    public List<Review> getListsReview() {
        List<Review> reviews = this.rRepo.findAll();
        return reviews;
    }
    
    @Override
    public boolean add(Map<String, String> params, MultipartFile imageUrl) {
        Review r = new Review();
//        if (!imageUrl.isEmpty()) {
//            try {
//                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//                p.setImageUrl(res.get("secure_url").toString());
//            } catch (IOException ex) {
//                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        this.rRepo.save(r);
        return true;
    }
    
    @Override
    public boolean update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id) {
        Review r = this.rRepo.findById(id).get();
//        if (!imageUrl.isEmpty()) {
//            try {
//                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//                p.setImageUrl(res.get("secure_url").toString());
//            } catch (IOException ex) {
//                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        this.rRepo.save(r);
        return true;
    }
    
    @Override
    public boolean delete(int id) {
        this.rRepo.delete(this.rRepo.findById(id).get());
        return true;
    }
}
