/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.Pojo.PaymentMethod;
import com.tdkhoa.ecommerce.Pojo.Voucher;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Khoa Tran
 */
public interface VoucherService {
    List<Voucher> getListVouchers();
    Voucher add(Map<String, String> params);
    Voucher update(Map<String, String> params, @PathVariable(value = "id") int id);
    Voucher delete(int id);
}
