/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.DTO.ProductDTO;
import com.tdkhoa.ecommerce.DTO.SearchDTO;
import com.tdkhoa.ecommerce.DTO.ShopDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<Product> getListProductsShop(User user) {
        Shop shop = sServ.findShopByUserId(user);
        List<Product> products = this.pRepo.findProductByShopId(shop);
        return products;
    }

    @Override
    public Product add(Map<String, String> params, MultipartFile thumbnail, User user, List<MultipartFile> file) {
        Shop shop = this.sServ.findShopByUserId(user);
        if (shop != null && shop.getStatus() == 1) {
            Product p = new Product();
            p.setName(params.get("name"));
            p.setDescription(params.get("description"));
            p.setIsDeleted(0);
            p.setPrice(Integer.parseInt(params.get("price")));
            p.setQty(Integer.parseInt(params.get("qty")));
            p.setSold(0);
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

            return p;
        }
        return null;
    }

    @Override
    public Product update(Map<String, String> params, MultipartFile thumbnail, @PathVariable(value = "id") int id, List<MultipartFile> file, User user) {
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
        return p;
    }

    @Override
    public Product delete(int id, User user) {
        Product p = this.pRepo.findById(id).get();
        Shop shop = sServ.findShopByUserId(user);
        if (shop != null) {
            if (p.getShopId().getId() == shop.getId()) {
                p.setIsDeleted(1);
                this.pRepo.save(p);
                return p;
            }
        }
        return null;
    }

    @Override
    public Product getProductById(int id) {
        return this.pRepo.findById(id).get();
    }

    @Override
    public List<Product> getListProducts() {
        return this.pRepo.findAll(Sort.by(Sort.Direction.DESC, "sold"));
    }

    @Override
    public SearchDTO search(Map<String, String> params) {
        String kw = params.get("keyword");
        ShopDTO sDTO = new ShopDTO();
        List<ProductDTO> listProductsDTO = new ArrayList<>();
        if (kw != null) {
            List<Product> listProducts = this.pRepo.search(kw);
            Shop shop = this.sServ.search(kw);
            if (!listProducts.isEmpty()) {
                for (Product p : listProducts) {
                    listProductsDTO.add(this.convertToDTO(p));
                }
                if (shop != null) {
                    sDTO = ShopDTO.builder()
                            .name(shop.getName())
                            .imageUrl(shop.getImageUrl())
                            .description(shop.getDescription())
                            .address(shop.getAddress())
                            .build();
                } else {
                    sDTO = listProductsDTO.get(0).getShop();
                }
            } else {
                return null;
            }
            SearchDTO searchDTO = SearchDTO.builder()
                    .products(listProductsDTO)
                    .shop(sDTO)
                    .build();
            return searchDTO;
        }
        return null;
    }

    @Override
    public ProductDTO convertToDTO(Product p) {
        ShopDTO shopDTO = ShopDTO.builder()
                .name(p.getShopId().getName())
                .imageUrl(p.getShopId().getImageUrl())
                .description(p.getShopId().getDescription())
                .address(p.getShopId().getAddress())
                .status(p.getShopId().getStatus())
                .build();

        ProductDTO pDTO = ProductDTO.builder()
                .name(p.getName())
                .description(p.getDescription())
                .category(p.getCategoryId().getName())
                .price(p.getPrice())
                .qty(p.getQty())
                .thumbnail(p.getThumbnail())
                .shop(shopDTO)
                .build();
        return pDTO;
    }

}
