/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.DTO;

import com.tdkhoa.ecommerce.Pojo.Voucher;
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
public class CartDTO {
    private int id;
    private int count;
    private int price;
    private Voucher voucher;
}
