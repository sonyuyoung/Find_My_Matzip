package com.matzip.controller;

import com.matzip.dto.*;
import com.matzip.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import com.matzip.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
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

    //원래 이걸로 하려고했는데 불러오는걸 못해서 바꿈 ,,, 확인해볼것
//    @GetMapping(value = "/restaurant/main")
//    public String restaurantMain(RestaurantSearchDto restaurantSearchDto, Optional<Integer> page, Model model){
//
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
//        Page<MainRestaurantDto> restaurants = restaurantService.getMainRestaurantPage(restaurantSearchDto, pageable);
//
//        model.addAttribute("restaurants", restaurants);
//        model.addAttribute("restaurantSearchDto", restaurantSearchDto);
//        model.addAttribute("maxPage", 5);
//
//        return "/restaurant/restaurant";
//    }

    //식당상세페이지 매핑
    @GetMapping(value = "/restaurant/{res_id}")
    public String boardDtl(Model model, @PathVariable("res_id") String res_id){
        RestaurantFormDto restaurantFormDto = restaurantService.getRestaurantDtl(res_id);
        model.addAttribute("restaurant", restaurantFormDto);
        return "restaurant/restaurantDtl";
    }


}