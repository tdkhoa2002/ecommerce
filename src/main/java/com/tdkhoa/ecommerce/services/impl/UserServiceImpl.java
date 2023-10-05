/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.DTO.AddressDTO;
import com.tdkhoa.ecommerce.DTO.OrderDTO;
import com.tdkhoa.ecommerce.DTO.UserDTO;
import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.UserRepository;
import com.tdkhoa.ecommerce.services.AddressService;
import com.tdkhoa.ecommerce.services.OrderService;
import com.tdkhoa.ecommerce.services.ReviewService;
import com.tdkhoa.ecommerce.services.ShopService;
import com.tdkhoa.ecommerce.services.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository uRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private AddressService addressServ;
    private Map<User, Boolean> checkVerifyPassword = new HashMap<>();

    public List<UserDTO> getListUsers() {
        List<User> listUsers = this.uRepo.findAll();
        List<UserDTO> listUsersDTO = new ArrayList<>();
        for (User u : listUsers) {
            listUsersDTO.add(convertToDTO(u));
        }
        return listUsersDTO;
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User user = new User();
        user.setUsername(params.get("username"));
        user.setPassword(this.passwordEncoder.encode(params.get("password")));
        user.setEmail(params.get("email"));
        user.setPhone(params.get("phone"));
        user.setFullName(params.get("full_Name"));
        user.setRedFlag(0);
        user.setRoleName(user.USER);
//        user.setAvatar(null);
        if (!avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            user.setAvatar(null);
        }
        this.uRepo.save(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return uRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = uRepo.findByUsername(username);

        if (users == null) {
            throw new UsernameNotFoundException("Không tồn tại!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return new org.springframework.security.core.userdetails.User(
                users.getUsername(), users.getPassword(), authorities);
    }

    @Override
    public User getUserLogining() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = this.findByUsername(userDetails.getUsername());
            return user;
        }
        return null;
    }

    @Override
    public UserDTO convertToDTO(User u) {
        Set<Order1> listOrders = u.getOrder1Set();
        Set<OrderDTO> listOrdersDTO = new HashSet<>();
        for (Order1 o : listOrders) {
            OrderDTO oDTO = OrderDTO.builder()
                    .id(o.getId())
                    .total_amount(o.getTotalAmount())
                    .createdTime(o.getCreatedTime())
                    .payment(o.getPaymentId())
                    .voucher(o.getVoucherId())
                    .build();
            listOrdersDTO.add(oDTO);
        }

        UserDTO userDTO = UserDTO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .password(u.getPassword())
                .email(u.getEmail())
                .phone(u.getPhone())
                .listAdresses(this.addressServ.convertToDTO(u.getAddressSet()))
                .listOrders(listOrdersDTO)
                //                .listReviews()
                //                .shop(u.getShopSet())
                .build();

        return userDTO;
    }

    @Override
    public User editProfile(Map<String, String> params, MultipartFile avatar, User u) {
        u.setUsername(params.get("username"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        u.setFullName(params.get("full_Name"));
        if (!avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.uRepo.save(u);
        return u;
    }

    @Override
    public boolean verifyPassword(User u, Map<String, String> params) {
        if (passwordEncoder.matches(params.get("password"), u.getPassword())) {
            checkVerifyPassword.put(u, true);
            return true;
        }
        return false;
    }

    @Override
    public User changePassword(User u, Map<String, String> params) {
        if (checkVerifyPassword.get(u)) {
            u.setPassword(this.passwordEncoder.encode(params.get("password")));
            this.uRepo.save(u);
            return u;
        }
        return null;
    }
}
