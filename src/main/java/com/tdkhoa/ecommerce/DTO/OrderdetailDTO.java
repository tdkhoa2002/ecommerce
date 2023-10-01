/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.DTO;

import com.tdkhoa.ecommerce.Pojo.Order1;
import com.tdkhoa.ecommerce.Pojo.Product;
import com.tdkhoa.ecommerce.Pojo.Shop;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Khoa Tran
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderdetailDTO {
    private Integer id;
    private Integer qty;
    private Date createTime;
    private Integer status;
    private UserDTO user;
    private ProductDTO product;
    private ShopDTO shop;
    private OrderDTO order;
}
