/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Banner;
import com.tdkhoa.ecommerce.services.BannerService;
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
import org.springframework.web.bind.annotation.RequestBody;
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
public class ApiBannerController {
    @Autowired
    private BannerService bServ;
    
    @GetMapping("/banners/")
    @CrossOrigin
    public ResponseEntity<List<Banner>> list() {
        return new ResponseEntity<>(this.bServ.getListBanners(), HttpStatus.OK);
    }
    
    @PostMapping("/create_banner/")
    @CrossOrigin
    public ResponseEntity<Boolean> add(@RequestParam Map<String, String> params, @RequestPart MultipartFile imageUrl) {
        return new ResponseEntity<>(this.bServ.add(params, imageUrl), HttpStatus.CREATED);
    }
    
    @PostMapping("/update_banner/{id}/")
    @CrossOrigin
    public ResponseEntity<Boolean> update(@RequestPart MultipartFile imageUrl, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.bServ.update(imageUrl, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_banner/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.bServ.delete(id), HttpStatus.OK);
    }
}
