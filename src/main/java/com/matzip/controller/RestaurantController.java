package com.matzip.controller;

import lombok.RequiredArgsConstructor;
import com.matzip.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/map")
    public String findAll(Model model){
      //  List<RestaurantDto> restaurantDtoList = restaurantService.findAll();
      //  model.addAttribute("restaurantList",restaurantDtoList);

        return "map/mapForm";
    }
}