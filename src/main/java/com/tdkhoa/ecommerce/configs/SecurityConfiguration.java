/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.configs;


import com.tdkhoa.ecommerce.security.AuthEntryPointJwt;
import com.tdkhoa.ecommerce.security.AuthTokenFilter;
import com.tdkhoa.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 *
 * @author Khoa Tran
 */
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(PasswordEncoder);

        return authProvider;
    }

   @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }
  

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth
                        -> auth.requestMatchers("/**").permitAll().
                                requestMatchers(HttpMethod.GET, "/api/**").authenticated().
                                requestMatchers( HttpMethod.GET, "/api/admin/**").hasAnyAuthority("ROLE_ADMIN").
                                requestMatchers(HttpMethod.POST, "/api/admin/**").hasAnyAuthority("ROLE_ADMIN").
                                requestMatchers(HttpMethod.PUT, "/api/admin/**").hasAnyAuthority("ROLE_ADMIN").
                                requestMatchers(HttpMethod.DELETE, "/api/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(  ), UsernamePasswordAuthenticationFilter.class);
//        http.logout(logout -> logout.logoutUrl("/api/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler);

        return http.build();
    }

}
