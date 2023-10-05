/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.DTO.EmailDTO;
import com.tdkhoa.ecommerce.services.EmailService;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Khoa Tran
 */
@RestController
@RequestMapping("/api")
public class ApiEmailController {
    @Autowired 
    private EmailService emailService;
 
    // Sending a simple Email
    @PostMapping("/shop/verify-mail/")
    public String sendMail(@RequestParam Map<String, String> params)
    {
        String status = emailService.sendMailVerifyShop(params);
 
        return status;
    }
 
    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment/") public String sendMailWithAttachment(
        @RequestBody EmailDTO details)
    {
        String status
            = emailService.sendMailWithAttachment(details);
 
        return status;
    }
}
