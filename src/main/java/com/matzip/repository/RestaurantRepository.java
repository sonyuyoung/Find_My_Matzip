package com.matzip.repository;

import com.matzip.entity.Board;
import com.matzip.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestaurantRepository extends JpaRepository<Restaurant, String>,
        QuerydslPredicateExecutor<Restaurant>, RestaurantRepositoryCustom{
     Restaurant findByresId(String resId);

}