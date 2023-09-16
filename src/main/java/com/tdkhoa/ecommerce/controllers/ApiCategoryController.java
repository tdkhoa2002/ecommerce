/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Banner;
import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.services.CategoryService;
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
    
    @GetMapping("/categories/")
    @CrossOrigin
    public ResponseEntity<List<Category>> list() {
        return new ResponseEntity<>(this.cateServ.getListCategories(), HttpStatus.OK);
    }
    
    @PostMapping("/create_category/")
    @CrossOrigin
    public ResponseEntity<Boolean> add(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.cateServ.add(params), HttpStatus.CREATED);
    }
    
    @PostMapping("/update_category/{id}/")
    @CrossOrigin
    public ResponseEntity<Boolean> update(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.cateServ.update(params, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_category/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.cateServ.delete(id), HttpStatus.OK);
    }
}
