package com.matzip.repository;

import com.matzip.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, String>{
    //위도와 경도로 조회
   // List<Restaurant> findByres_latAndres_lng(String res_lat, String res_lng);

}