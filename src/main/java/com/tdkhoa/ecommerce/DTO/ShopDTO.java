/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.DTO;

import com.tdkhoa.ecommerce.Pojo.Orderdetail;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Khoa Tran
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {
    private Integer id;
    private String name;
    private String description;
    private String address;
    private String imageUrl;
    private Integer status;
    private UserDTO userDTO;
    private Set<Orderdetail> orderDetailDTO;
}
