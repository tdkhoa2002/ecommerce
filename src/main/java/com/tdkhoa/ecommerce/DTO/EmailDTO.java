/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Khoa Tran
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
