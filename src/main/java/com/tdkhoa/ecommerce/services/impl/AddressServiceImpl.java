/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.AddressDTO;
import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.AddressRepository;
import com.tdkhoa.ecommerce.services.AddressService;
import java.util.ArrayList;
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

    @Override
    public List<Address> getListAddress() {
        return this.aRepo.findAll();
    }

    @Override
    public boolean add(Map<String, String> params, User u) {
        if (u.getAddressSet().size() < 4) {
            Address addr = new Address();
            addr.setAddress(params.get("address"));
            addr.setWard(params.get("ward"));
            addr.setCity(params.get("city"));
            Set<User> user = new HashSet<>();
            user.add(u);
            addr.setUserSet(user);
            this.aRepo.save(addr);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Map<String, String> params, int id, User u) {
        Address addr = this.aRepo.findById(id).get();
        if (addr.getUserSet().contains(u)) {
            addr.setAddress(params.get("address"));
            addr.setCity(params.get("city"));
            addr.setWard(params.get("ward"));
            this.aRepo.save(addr);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id, User u) {
        Address addr = this.aRepo.findById(id).get();
        if(addr.getUserSet().contains(u)) {
            this.aRepo.delete(addr);
            return true;
        }
        return false;
    }

    @Override
    public Set<Address> getAddressOfUser(User user) {
        Set<Address> listAddresses = user.getAddressSet();
        return listAddresses;
    }

    @Override
    public Set<AddressDTO> convertToDTO(Set<Address> listAddresses) {
        Set<AddressDTO> listAddressesDTO = new HashSet<>();
        for (Address a : listAddresses) {
            AddressDTO addDTO = AddressDTO.builder()
                .id(a.getId())
                .address(a.getAddress())
                .ward(a.getWard())
                .city(a.getCity())
                .build();
            listAddressesDTO.add(addDTO);
        }

        return listAddressesDTO;
    }
}
