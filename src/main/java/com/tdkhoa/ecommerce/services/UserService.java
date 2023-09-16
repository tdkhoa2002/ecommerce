/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.Pojo.User;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
public interface UserService extends UserDetailsService {
    boolean addUser(Map<String, String> params, MultipartFile avatar);
    UserDetails loadUserByUsername(String username);
    User findByUsername(String user);
}
