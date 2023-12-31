/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.Pojo.Voucher;
import com.tdkhoa.ecommerce.services.UserService;
import com.tdkhoa.ecommerce.services.VoucherService;
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
public class ApiVoucherController {

    @Autowired
    private VoucherService vServ;
    @Autowired
    private UserService uServ;

    @GetMapping("/admin/vouchers/")
    @CrossOrigin
    public ResponseEntity<List<Voucher>> list() {
        User user = this.uServ.getUserLogining();
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(this.vServ.getListVouchers(), HttpStatus.OK);
        }
        return null;
    }

    @PostMapping("/admin/create_voucher/")
    @CrossOrigin
    public ResponseEntity<Voucher> add(@RequestParam Map<String, String> params) {
        User user = this.uServ.getUserLogining();
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(this.vServ.add(params), HttpStatus.CREATED);
        }
        return null;
    }

    @PostMapping("/admin/update_voucher/{id}/")
    @CrossOrigin
    public ResponseEntity<Voucher> update(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
        User user = this.uServ.getUserLogining();
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(this.vServ.update(params, id), HttpStatus.CREATED);
        }
        return null;
    }

    @DeleteMapping("/admin/delete_voucher/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Voucher> delete(@PathVariable(value = "id") int id) {
        User user = this.uServ.getUserLogining();
        if (user.getRoleName().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(this.vServ.delete(id), HttpStatus.OK);
        }
        return null;
    }
    
    @GetMapping("/vouchers/")
    @CrossOrigin
    public ResponseEntity<List<Voucher>> getListVoucher() {
         return new ResponseEntity<>(this.vServ.getListVouchers(), HttpStatus.OK);
    }
}
