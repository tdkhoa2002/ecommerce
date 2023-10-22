/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.Pojo.Voucher;
import com.tdkhoa.ecommerce.repositories.VoucherRepository;
import com.tdkhoa.ecommerce.services.VoucherService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository vRepo;

    @Override
    public List<Voucher> getListVouchers() {
        return this.vRepo.getListVoucher(0);
    }

    @Override
    public Voucher add(Map<String, String> params) {
        Voucher v = new Voucher();
        int value = Integer.parseInt(params.get("value"));
        int quantity = Integer.parseInt(params.get("quantity"));
        if(value > 0 && value <= 100 && quantity > 0 && quantity <= 10000) {
            v.setName(params.get("name"));
            v.setCode(params.get("code"));
            v.setQuantity(quantity);
            v.setValue(value);
            v.setIsDeleted(0);
            this.vRepo.save(v);
            return v;
        }
        return null;
    }

    @Override
    public Voucher update(Map<String, String> params, int id) {
        int value = Integer.parseInt(params.get("value"));
        int quantity = Integer.parseInt(params.get("quantity"));
        System.out.println(value);
        System.out.println(quantity);
        if (value > 0 && value <= 100 && quantity > 0 && quantity <= 10000) {
            Voucher v = this.vRepo.findById(id).get();
            v.setName(params.get("name"));
            v.setCode(params.get("code"));
            v.setQuantity(quantity);
            v.setValue(value);
            v.setIsDeleted(Integer.parseInt(params.get("isDeleted")));
            this.vRepo.save(v);
            return v;
        }
        return null;
        
    }

    @Override
    public Voucher delete(int id) {
        Voucher v = this.vRepo.findById(id).get();
        //Set v.is_deleted = 1;
        v.setIsDeleted(1);
        this.vRepo.save(v);
        return v;
    }

    @Override
    public Voucher getVoucherById(int id) {
        return this.vRepo.findById(id).get();
    }
}
