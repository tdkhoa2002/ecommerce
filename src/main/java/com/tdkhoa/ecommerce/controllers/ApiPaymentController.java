/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.PaymentMethod;
import com.tdkhoa.ecommerce.services.PaymentService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Khoa Tran
 */
@RestController
@RequestMapping("/api")
public class ApiPaymentController {
    @Autowired
    private PaymentService pServ;
    
    @GetMapping("/payments/")
    @CrossOrigin
    public ResponseEntity<List<PaymentMethod>> list() {
        return new ResponseEntity<>(this.pServ.getListPayments(), HttpStatus.OK);
    }
    
    @PostMapping("/create_payment/")
    @CrossOrigin
    public ResponseEntity<PaymentMethod> add(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.pServ.add(params), HttpStatus.CREATED);
    }
    
    @PostMapping("/update_payment/{id}/")
    @CrossOrigin
    public ResponseEntity<PaymentMethod> update(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.pServ.update(params, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_payment/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<PaymentMethod> delete(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.pServ.delete(id), HttpStatus.OK);
    }
}
