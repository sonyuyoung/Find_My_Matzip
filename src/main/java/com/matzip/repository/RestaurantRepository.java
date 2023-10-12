package com.matzip.repository;

import com.matzip.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, String>{
    // Restaurant findByRes_id(String res_id);

}