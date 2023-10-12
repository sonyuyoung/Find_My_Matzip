package com.matzip.controller;

import com.matzip.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import com.matzip.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/map")
    public String findAll(Model model){
      List<RestaurantDto> restaurantDtoList = restaurantService.findAll();
      System.out.println("restaurantDtoList 의 소스 확인 동네 1: "+restaurantDtoList.get(0).getRes_name());
      model.addAttribute("restaurantList",restaurantDtoList);

        return "map/mapForm";
    }
}