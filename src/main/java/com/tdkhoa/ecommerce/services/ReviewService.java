/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.services;

import com.tdkhoa.ecommerce.Pojo.Review;
import com.tdkhoa.ecommerce.Pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
public interface ReviewService {
    List<Review> getListsReviewByProductId(int idProduct);
    Review add(Map<String, String> params, MultipartFile imageUrl, User u, int idProduct);
    Review update(Map<String, String> params, MultipartFile imageUrl, @PathVariable(value = "id") int id, User u);
    Review delete(int id, User u);
}
