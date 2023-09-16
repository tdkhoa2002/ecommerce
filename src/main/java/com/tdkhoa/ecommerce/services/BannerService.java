/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.Pojo.Banner;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Khoa Tran
 */
public interface BannerService {
    List<Banner> getListBanners();
    boolean add(Map<String, String> params, MultipartFile imageUrl);
    boolean update(MultipartFile imageUrl, @PathVariable(value = "id") int id);
    boolean delete(int id);
}
