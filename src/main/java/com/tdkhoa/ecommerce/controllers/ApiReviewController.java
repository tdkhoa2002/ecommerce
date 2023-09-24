/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Review;
import com.tdkhoa.ecommerce.services.ReviewService;
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
public class ApiReviewController {
    @Autowired
    private ReviewService rServ;
    @Autowired
    private UserService uServ;
    
    @GetMapping("/reviews/")
    @CrossOrigin
    public ResponseEntity<List<Review>> list(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.rServ.getListsReviewByProductId(id), HttpStatus.OK);
    }
    
    @PostMapping("/create_review/{productId}/")
    @CrossOrigin
    public ResponseEntity<Review> add(@RequestParam Map<String, String> params, @RequestPart MultipartFile imageUrl, @PathVariable(value = "productId") int id) {
        return new ResponseEntity<>(this.rServ.add(params, imageUrl, this.uServ.getUserLogining(), id), HttpStatus.CREATED);
    }
    
    @PostMapping("/update_review/{id}/")
    @CrossOrigin
    public ResponseEntity<Review> update(@RequestParam Map<String, String> params, @RequestPart MultipartFile imageUrl, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.rServ.update(params, imageUrl, id, this.uServ.getUserLogining()), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_review/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id) {
        this.rServ.delete(id, this.uServ.getUserLogining());
    }
}
