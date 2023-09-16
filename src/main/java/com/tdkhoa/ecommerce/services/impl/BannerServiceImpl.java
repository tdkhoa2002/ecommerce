/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Banner;
import com.tdkhoa.ecommerce.repositories.BannerRepository;
import com.tdkhoa.ecommerce.services.BannerService;
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
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bRepo;
    @Autowired
    private Cloudinary cloudinary;
    
    @Override
    public List<Banner> getListBanners() {
        List<Banner> banners = this.bRepo.findAll();
        return banners;
    }
    
    @Override
    public boolean add(Map<String, String> params, MultipartFile imageUrl) {
        Banner banner = new Banner();
        if (!imageUrl.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                banner.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.bRepo.save(banner);
        return true;
    }
    
    @Override
    public boolean update(MultipartFile imageUrl, @PathVariable(value = "id") int id) {
        Banner b = this.bRepo.findById(id).get();
        if (!imageUrl.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(imageUrl.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                b.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(BannerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.bRepo.save(b);
        return true;
    }
    
    @Override
    public boolean delete(int id) {
        this.bRepo.delete(this.bRepo.findById(id).get());
        return true;
    }
}
