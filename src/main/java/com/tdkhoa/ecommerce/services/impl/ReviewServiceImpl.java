/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.DTO.StarDTO;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Review;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.ProductRepository;
import com.tdkhoa.ecommerce.repositories.ReviewRepository;
import com.tdkhoa.ecommerce.services.ReviewService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository rRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ProductRepository pRepo;

    @Override
    public List<Review> getListsReviewByProductId(int idProduct) {
        List<Review> reviews = this.rRepo.findAll();
        return reviews;
    }

    @Override
    public Review add(Map<String, String> params, MultipartFile imageUrl, User user, int idProduct) {
        Product p = this.pRepo.findById(idProduct).get();
        Set<Order1> listOrder = user.getOrder1Set();
        for (Order1 o : listOrder) {
            Set<Orderdetail> listOD = o.getOrderdetailSet();
            for (Orderdetail od : listOD) {
                if (od.getProductId() == p) {
                    Review r = new Review();
                    r.setContent(params.get("content"));
                    r.setUserId(user);
                    r.setProductId(p);
                    r.setCreateTime(new Date());
                    int star = Integer.parseInt(params.get("star"));
                    if (star > 0 && star <= 5) {
                        r.setStar(star);
                    } else {
                        return null;
                    }
                    if (!imageUrl.isEmpty()) {
                        try {
                            Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                            r.setImageUrl(res.get("secure_url").toString());
                        } catch (IOException ex) {
                            Logger.getLogger(ReviewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    this.rRepo.save(r);
                    return r;
                }
            }
        }
        return null;
    }

    @Override
    public Review update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id, User user) {
        Review r = this.rRepo.findById(id).get();
        if (r.getUserId().equals(user)) {
            r.setContent(params.get("content"));
            if (!imageUrl.isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    r.setImageUrl(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(ReviewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.rRepo.save(r);
            return r;
        }
        return null;
    }

    @Override
    public Review delete(int id, User user) {
        Review r = this.rRepo.findById(id).get();
        if (r.getUserId().equals(user)) {
            this.rRepo.delete(this.rRepo.findById(id).get());
            return r;
        }
        return null;
    }

    @Override
    public List<StarDTO> getCountStarProduct(int idProduct) {
        Product p = this.pRepo.findById(idProduct).get();
        List<StarDTO> listStarDTO = this.rRepo.countStarProduct(p);
        return listStarDTO;
    }
}
