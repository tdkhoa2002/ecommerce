/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.repositories.UserRepository;
import com.tdkhoa.ecommerce.services.UserService;
import java.io.IOException;
import java.util.HashSet;
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

//    public List<User> getListUsers() {
//        return;
//    }
    @Override
    public boolean addUser(Map<String, String> params, MultipartFile avatar) {
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
        return true;
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
//        Optional<Role> role = roleService.getRoleById(users.getRoleId().getId());
//        String roleName=role.get().getRoleName();
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
}
