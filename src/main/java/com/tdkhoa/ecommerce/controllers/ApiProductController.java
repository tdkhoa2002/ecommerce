/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Banner;
import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.services.ProductService;
import com.tdkhoa.ecommerce.services.ShopService;
import com.tdkhoa.ecommerce.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@RestController
@RequestMapping("/api")
public class ApiProductController {
    @Autowired
    private ProductService pServ;
    @Autowired
    private UserService uServ;
    @Autowired
    private ShopService sServ;
    
    @GetMapping("/shop/products/")
    @CrossOrigin
    public ResponseEntity<List<Product>> list() {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.pServ.getListProducts(user), HttpStatus.OK);
    }
    
    @PostMapping("/shop/create_product/")
    @CrossOrigin
    public ResponseEntity<Boolean> add(@RequestParam Map<String, String> params, @RequestPart MultipartFile thumbnail, @RequestPart List<MultipartFile> file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = uServ.findByUsername(userDetails.getUsername());

             return new ResponseEntity<>(this.pServ.add(params, thumbnail, user, file), HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/shop/update_product/{id}/")
    @CrossOrigin
    public ResponseEntity<Boolean> update(@RequestParam Map<String, String> params, @RequestPart MultipartFile thumbnail, @PathVariable(value = "id") int id, @RequestPart List<MultipartFile> file) {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.pServ.update(params, thumbnail, id, file, user), HttpStatus.CREATED);
    }

    @DeleteMapping("/shop/delete_product/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id) {
        User user = this.uServ.getUserLogining();
        this.pServ.delete(id, user);
    }
}
