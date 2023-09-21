/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.Banner;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.services.AddressService;
import com.tdkhoa.ecommerce.services.UserService;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Khoa Tran
 */
@RestController
@RequestMapping("/api")
public class ApiAddressController {

    @Autowired
    private AddressService addrServ;
    @Autowired
    private UserService uServ;

    @GetMapping("/admin/addresses")
    @CrossOrigin
    public ResponseEntity<List<Address>> list() {
        return new ResponseEntity<>(this.addrServ.getListAddress(), HttpStatus.OK);
    }

    @PostMapping("/create_address/")
    @CrossOrigin
    public ResponseEntity<Boolean> add(@RequestParam Map<String, String> params) {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.addrServ.add(params, user), HttpStatus.CREATED);
    }

    @PostMapping("/update_address/{id}/")
    @CrossOrigin
    public ResponseEntity<Boolean> update(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.addrServ.update(params, id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete_address/{id}/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") int id) {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.addrServ.delete(id, user), HttpStatus.OK);
    }

    @GetMapping("/user/address/")
    @CrossOrigin
    public ResponseEntity<Set<Address>> getAddresses() {
        User u = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.addrServ.getAddressOfUser(u), HttpStatus.OK);
    }
}
