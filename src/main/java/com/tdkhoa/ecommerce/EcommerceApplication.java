package com.tdkhoa.ecommerce;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EcommerceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
    
    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "de3yhowd4",
                        "api_key", "945421312381893",
                        "api_secret", "JbKRQ8KcHDDW9fSYDyYwiq4nmEo",
                        "secure", true));
        return cloudinary;
    }
}
