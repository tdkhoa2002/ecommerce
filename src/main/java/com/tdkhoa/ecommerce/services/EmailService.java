/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.DTO.EmailDTO;
import com.tdkhoa.ecommerce.Pojo.Shop;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Khoa Tran
 */
public interface EmailService {
    public static Map<String, String> checkVerifyEmailShop = new HashMap<>();
    String sendMailVerifyShop(Map<String, String> params);
    String sendMailOrder(Shop s);
    String sendMailActiveShop(Shop s);
    String generateCode();
    String sendMailWithAttachment(EmailDTO details);
}
