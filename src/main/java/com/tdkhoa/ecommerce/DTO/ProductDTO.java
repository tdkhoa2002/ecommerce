/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.DTO;

import com.tdkhoa.ecommerce.Pojo.Image;
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
public class ProductDTO {
    private Integer id;
    private Integer productId;
    private String name;
    private String thumbnail;
    private String description;
    private Integer qty;
    private Integer price;
    private Integer quantity;
    private CategoryDTO category;
    private Integer status;
    private ShopDTO shop;
    private Set<Image> imageSet;
}
