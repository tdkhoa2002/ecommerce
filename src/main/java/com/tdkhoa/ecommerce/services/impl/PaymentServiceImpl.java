/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.Pojo.Payment;
import com.tdkhoa.ecommerce.repositories.PaymentRepository;
import com.tdkhoa.ecommerce.services.PaymentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository payRepo;

    @Override
    public List<Payment> getListPayments() {
        return this.payRepo.findAll();
    }

    @Override
    public Payment add(Map<String, String> params) {
        Payment p = new Payment();
        p.setName(params.get("name"));
        this.payRepo.save(p);
        return p;
    }

    @Override
    public Payment update(Map<String, String> params, int id) {
        Payment p = this.payRepo.findById(id).get();
        
        p.setName(params.get("name"));
        
        this.payRepo.save(p);
        return p;
    }

    @Override
    public Payment delete(int id) {
        Payment p = this.payRepo.findById(id).get();
        this.payRepo.delete(p);
        return null;
    }

    @Override
    public Payment getPaymentById(int id) {
        return this.payRepo.findById(id).get();
    }
}
