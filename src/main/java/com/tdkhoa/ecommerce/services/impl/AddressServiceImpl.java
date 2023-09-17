/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.AddressUser;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.AddressRepository;
import com.tdkhoa.ecommerce.repositories.AddressUserRepository;
import com.tdkhoa.ecommerce.services.AddressService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository aRepo;
    @Autowired
    private AddressUserRepository auRepo;

    @Override
    public List<Address> getListAddress() {
        return this.aRepo.findAll();
    }

    @Override
    public boolean add(Map<String, String> params, User u) {
//        List<Address> listAddress = this.aRepo.getAddressOfUser(u.getId());
//        if (listAddress.size() < 4) {
            Address addr = new Address();
            addr.setAddress(params.get("address"));
            addr.setWard(params.get("ward"));
            addr.setCity(params.get("city"));
//            Set<User> user = new HashSet<>();
//            user.add(u);
//            addr.setUserSet(user);
            this.aRepo.save(addr);
            
            AddressUser aUser = new AddressUser();
            aUser.setAddress(addr);
            aUser.setUser(u);
            this.auRepo.save(aUser);
            return true;
        }
//        return false;
//    }

    @Override
    public boolean update(Map<String, String> params, int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
