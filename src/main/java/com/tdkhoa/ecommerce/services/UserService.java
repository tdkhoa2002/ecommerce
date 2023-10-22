/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.DTO.UserDTO;
import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
public interface UserService extends UserDetailsService {
    List<UserDTO> getListUsers();
    User addUser(Map<String, String> params, MultipartFile avatar);
    UserDetails loadUserByUsername(String username);
    User findByUsername(String user);
    User getUserLogining();
    User editProfile(Map<String, String> params, MultipartFile avatar, User u);
    UserDTO convertToDTO(User u);
    boolean verifyPassword(User u, Map<String, String> params);
    User changePassword(User u, Map<String, String> params);
    boolean changeStatus(User u, Map<String, String> params);
}
