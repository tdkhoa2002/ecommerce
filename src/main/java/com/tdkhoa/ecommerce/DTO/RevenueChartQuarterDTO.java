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
public class RevenueChartQuarterDTO {
    private int id;
    private int odYear;
    private int odQuarter;
    private Long total;
    
    public RevenueChartQuarterDTO(int year, int quarter, Long total) {
        this.odYear = year;
        this.odQuarter = quarter;
        this.total = total;
    }
}
