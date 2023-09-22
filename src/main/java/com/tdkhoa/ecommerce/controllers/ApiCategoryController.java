/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Banner;
import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.services.CategoryService;
import com.tdkhoa.ecommerce.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ApiCategoryController {
    @Autowired
    private CategoryService cateServ;
    @Autowired
    private UserService uServ;
    
    @GetMapping("/categories/")
    @CrossOrigin
    public ResponseEntity<List<Category>> list() {
        return new ResponseEntity<>(this.cateServ.getListCategories(), HttpStatus.OK);
    }
    
    @PostMapping("/admin/create_category/")
    @CrossOrigin
    public ResponseEntity<Boolean> add(@RequestParam Map<String, String> params, @RequestPart MultipartFile imageUrl) {
        User user = this.uServ.getUserLogining();
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(this.cateServ.add(params, imageUrl), HttpStatus.CREATED);
        }
        return null;
    }
    
    @PostMapping("/admin/update_category/{id}/")
    @CrossOrigin
    public ResponseEntity<Boolean> update(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id, @RequestPart MultipartFile imageUrl) {
        User user = this.uServ.getUserLogining();
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(this.cateServ.update(params, id, imageUrl), HttpStatus.CREATED);
        }
        return null;
    }

    @DeleteMapping("/admin/delete_category/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") int id) {
        User user = this.uServ.getUserLogining();
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(this.cateServ.delete(id), HttpStatus.OK);
        }
        return null;
    }
}
