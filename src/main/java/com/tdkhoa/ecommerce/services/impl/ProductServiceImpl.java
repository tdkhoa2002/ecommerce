/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.Pojo.Image;
import com.tdkhoa.ecommerce.repositories.CategoryRepository;
import com.tdkhoa.ecommerce.repositories.ImageRepository;
import com.tdkhoa.ecommerce.repositories.ProductRepository;
import com.tdkhoa.ecommerce.services.ProductService;
import com.tdkhoa.ecommerce.services.ShopService;
import java.io.IOException;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository pRepo;
    @Autowired
    private CategoryRepository cRepo;
    @Autowired
    private ImageRepository iRepo;
    @Autowired
    private ShopService sServ;
    @Autowired
    private Cloudinary cloudinary;

    public List<Product> getListProducts(User user) {
        Shop shop = sServ.findShopByUserId(user);
        List<Product> products = this.pRepo.findProductByShopId(shop);
        return products;
    }

    @Override
    public boolean add(Map<String, String> params, MultipartFile thumbnail, User user, List<MultipartFile> file) {
        Shop shop = this.sServ.findShopByUserId(user);
        if (shop != null) {
            Product p = new Product();
            p.setName(params.get("name"));
            p.setDescription(params.get("description"));
            p.setIsDeleted(0);
            int category_id = Integer.parseInt(params.get("category_id"));
            p.setCategoryId(this.cRepo.findById(category_id).get());
            p.setShopId(shop);
            if (!thumbnail.isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(thumbnail.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    p.setThumbnail(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.pRepo.save(p);

            //Save Images of Product
            for (MultipartFile f : file) {
                Image img = new Image();

                try {
                    Map res = this.cloudinary.uploader().upload(f.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    img.setImageUrl(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

                img.setProductId(p);
                this.iRepo.save(img);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean update(Map<String, String> params, MultipartFile thumbnail, @PathVariable(value = "id") int id, List<MultipartFile> file, User user) {
        Product p = this.pRepo.findById(id).get();

        p.setName(params.get("name"));
        p.setDescription(params.get("description"));
        p.setIsDeleted(0);
        int category_id = Integer.parseInt(params.get("category_id"));
        p.setCategoryId(this.cRepo.findById(category_id).get());
        Shop shop = sServ.findShopByUserId(user);
        p.setShopId(shop);
        if (!thumbnail.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(thumbnail.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                p.setThumbnail(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.pRepo.save(p);

        //Save Images of Product
        List<Image> images = this.iRepo.findImageByProductId(p);
        System.out.println(images);
        for (int i = 0; i < file.size(); i++) {
            Image image = images.get(i);
            MultipartFile f = file.get(i);
            try {
                Map res = this.cloudinary.uploader().upload(f.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                image.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.iRepo.save(image);
        }
        return true;
    }

    @Override
    public boolean delete(int id, User user) {
        Product p = this.pRepo.findById(id).get();
        Shop shop = sServ.findShopByUserId(user);
        if (shop != null) {
            if (p.getShopId().getId() == shop.getId()) {
                p.setIsDeleted(1);
                this.pRepo.save(p);
                return true;
            }
        }
        return false;
    }
}
