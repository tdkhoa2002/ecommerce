/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.Pojo.Address;
import com.tdkhoa.ecommerce.Pojo.Category;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Khoa Tran
 */
public interface AddressService {
    List<Address> getListAddress();
    boolean add(Map<String, String> params, User u);
    boolean update(Map<String, String> params, @PathVariable(value = "id") int id, User u);
    boolean delete(int id, User u);
    Set<Address> getAddressOfUser(User u);
}
