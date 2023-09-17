/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.repositories;

import com.tdkhoa.ecommerce.Pojo.AddressUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Khoa Tran
 */
public interface AddressUserRepository extends JpaRepository<AddressUser, Integer>{
    
}
