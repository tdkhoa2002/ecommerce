/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.tdkhoa.ecommerce.DTO.EmailDTO;
import com.tdkhoa.ecommerce.Pojo.Shop;
import com.tdkhoa.ecommerce.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    
    @Value("${name.app}")
    private String nameApp;

    // Method 1
    // To send a simple email
    public String sendMailVerifyShop(Map<String, String> params) {
        // Try block to check for exceptions
        try {
            EmailDTO eDTO = new EmailDTO();
            Date now = new Date();
            String code = this.generateCode();
            eDTO.setMsgBody(code);
            eDTO.setRecipient(params.get("email"));
            eDTO.setSubject("[" + this.nameApp + "] XÁC MINH EMAIL - " + now);
            // Creating a simple mail message
            checkVerifyEmailShop.put(eDTO.getRecipient(), code);
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(eDTO.getRecipient());
            mailMessage.setText(eDTO.getMsgBody());
            mailMessage.setSubject(eDTO.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    // Method 2
    // To send an email with attachment
    public String
            sendMailWithAttachment(EmailDTO details) {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                            new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

    @Override
    public String generateCode() {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        
        return String.format("%06d", number);
    }

    @Override
    public String sendMailOrder(Shop s) {
        try {
            EmailDTO eDTO = new EmailDTO();
            Date now = new Date();
            eDTO.setMsgBody("Bạn vừa nhận được 1 đơn hàng mới");
            eDTO.setRecipient(s.getEmail());
            eDTO.setSubject("[" + this.nameApp + "] THÔNG BÁO CÓ ĐƠN HÀNG MỚI - " + now);
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(eDTO.getRecipient());
            mailMessage.setText(eDTO.getMsgBody());
            mailMessage.setSubject(eDTO.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
    
    @Override
    public String sendMailActiveShop(Shop s) {
        try {
            EmailDTO eDTO = new EmailDTO();
            Date now = new Date();
            eDTO.setMsgBody("Cửa hàng của bạn đã được cho phép hoạt động");
            eDTO.setRecipient(s.getEmail());
            eDTO.setSubject("[" + this.nameApp + "] THÔNG BÁO CỬA HÀNG CỦA BẠN ĐÃ ĐƯỢC PHÉP HOẠT ĐỘNG");
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(eDTO.getRecipient());
            mailMessage.setText(eDTO.getMsgBody());
            mailMessage.setSubject(eDTO.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
