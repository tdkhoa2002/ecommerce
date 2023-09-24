/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.services.OrderService;
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
public class ApiShopController {
    @Autowired 
    private ShopService sServ;
    @Autowired
    private UserService uServ;
    @Autowired
    private OrderService oServ;
    
    @GetMapping("/admin/shops/")
    @CrossOrigin
    public ResponseEntity<List<Shop>> list() {
        return new ResponseEntity<>(this.sServ.getListShops(), HttpStatus.OK);
    }
    
    @PostMapping("/create_shop/")
    @CrossOrigin
    public ResponseEntity<Shop> add(@RequestParam Map<String, String> params, @RequestPart MultipartFile imageUrl) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = uServ.findByUsername(userDetails.getUsername());
            return new ResponseEntity<>(this.sServ.add(params, imageUrl, user), HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/update_shop/{id}/")
    @CrossOrigin
    public ResponseEntity<Shop> update(@RequestParam Map<String, String> params, @RequestPart MultipartFile imageUrl, @PathVariable(value = "id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = uServ.findByUsername(userDetails.getUsername());
            
            Shop s = this.sServ.getShopById(id);
            if (s.getUserId() == user) {
                return new ResponseEntity<>(this.sServ.update(params, imageUrl, id), HttpStatus.CREATED);
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/admin/delete_shop/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id) {
        User user = this.uServ.getUserLogining();
        if(user.getRoleName().equals("ROLE_ADMIN")) {
            this.sServ.delete(id);
        }
    }
    
    @PostMapping("/admin/active_shop/{id}/")
    @CrossOrigin
    public ResponseEntity<Shop> active(@PathVariable(value = "id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = uServ.findByUsername(userDetails.getUsername());
            if (user.getRoleName().equals("ROLE_ADMIN")) {
                return new ResponseEntity<>(this.sServ.activeShop(id, user), HttpStatus.CREATED);
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/shop/orders/{shopId}/")
    @CrossOrigin
    public ResponseEntity<List<Order1>> listOrders(@PathVariable(value = "shopId") int shopId) {
        
        return new ResponseEntity<>(this.oServ.getListOrders(), HttpStatus.OK);
    }
}
