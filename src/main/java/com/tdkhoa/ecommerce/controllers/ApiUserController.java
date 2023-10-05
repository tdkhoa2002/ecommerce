/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.controllers;

import com.tdkhoa.ecommerce.DTO.LoginDTO;
import com.tdkhoa.ecommerce.DTO.OrderdetailDTO;
import com.tdkhoa.ecommerce.DTO.UserDTO;
import com.tdkhoa.ecommerce.Pojo.User;
import com.tdkhoa.ecommerce.security.JwtUtils;
import com.tdkhoa.ecommerce.services.OrderDetailService;
import com.tdkhoa.ecommerce.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private UserService uServ;
    @Autowired
    private OrderDetailService odServ;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/admin/users/")
    @CrossOrigin
    public ResponseEntity<List<UserDTO>> getLisUsers() {
        return new ResponseEntity<>(this.uServ.getListUsers(), HttpStatus.OK);
    }

    @PostMapping(path = "/signin/")
    @CrossOrigin
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) throws Exception {
        authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        final UserDetails userDetails = uServ.loadUserByUsername(loginDTO.getUsername());
        User user = uServ.findByUsername(userDetails.getUsername());

        String jwtResponse = jwtUtils.generateJwtToken(userDetails);

        if (jwtResponse != null) {
            Cookie cookie = new Cookie("JWT_TOKEN", jwtResponse);
            cookie.setPath("/");
            cookie.setMaxAge(3600);

            response.addCookie(cookie);
            return ResponseEntity.ok().body(jwtResponse);
        } else {
            return ResponseEntity.badRequest().body("Username or password is invalid!");
        }
    }

    private void authenticate(String username, String password) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping(path = "/register/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    @CrossOrigin
    public ResponseEntity<?> register(@Valid @RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        User user = this.uServ.addUser(params, avatar);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //Profile
    @GetMapping("/user/profile/")
    @CrossOrigin
    public ResponseEntity<User> getProfileUser() {
        return new ResponseEntity<>(this.uServ.getUserLogining(), HttpStatus.OK);
    }

    @GetMapping("/user/purchase/")
    @CrossOrigin
    public ResponseEntity<List<OrderdetailDTO>> getOrderListUser(@RequestParam Map<String, String> params) {
        User u = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.odServ.getListOrderDetailsUser(u, params), HttpStatus.OK);
    }

    @PostMapping("/user/edit-profile/")
    @CrossOrigin
    public ResponseEntity<User> editProfile(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) throws Exception {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.uServ.editProfile(params, avatar ,user), HttpStatus.OK);
    }
    
    @PostMapping("/user/verify/password/")
    @CrossOrigin
    public ResponseEntity<Boolean> verifyPassword(@RequestParam Map<String, String> params) throws Exception {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.uServ.verifyPassword(user, params), HttpStatus.OK);
    }
    
    @PostMapping("/user/change-password/")
    @CrossOrigin
    public ResponseEntity<User> changePassword(@RequestParam Map<String, String> params) throws Exception {
        User user = this.uServ.getUserLogining();
        return new ResponseEntity<>(this.uServ.changePassword(user, params), HttpStatus.OK);
    }
}
