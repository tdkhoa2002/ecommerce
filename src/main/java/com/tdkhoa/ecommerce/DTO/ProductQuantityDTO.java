/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.DTO;

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
public class ProductQuantityDTO {

    private Long totalQuantity;
    private String productName;

    public ProductQuantityDTO(String productName, Long totalQuantity) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
    }
}
