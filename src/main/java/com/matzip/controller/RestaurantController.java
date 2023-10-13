package com.matzip.controller;

import com.matzip.dto.*;
import lombok.RequiredArgsConstructor;
import com.matzip.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/restaurant/new")
    public String restaurantForm(Model model){
        model.addAttribute("restaurantFormDto", new RestaurantFormDto());
        return "/restaurant/restaurantForm";
    }

    @GetMapping(value = "/restaurant/main")
    public String restaurantMain(RestaurantSearchDto restaurantSearchDto, Optional<Integer> page, Model model){

        List<RestaurantDto> restaurantDtoList = restaurantService.findAll();
        System.out.println("restaurantDtoList 의 소스 확인 동네 1: "+restaurantDtoList.get(0).getRes_name());
        model.addAttribute("restaurantList",restaurantDtoList);

        return "/restaurant/restaurant";
    }

}