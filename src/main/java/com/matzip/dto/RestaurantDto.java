package com.matzip.dto;

import com.matzip.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class RestaurantDto {

    private String res_id;       //식당 id

    private String res_name;//식당 이름

    private String res_district;//구군

    private String res_lat;//위도

    private String res_lng;//경도

    private String res_address;//식당 주소

    private String res_phone;//연락처

    private String operate_time;//운영 및 시간

    private String res_menu;//대표 메뉴

    private String res_image; //식당 이미지 url

    private String res_thumbnail; //썸네일 이미지 url

    private String res_intro; //가게 소개

    private static ModelMapper modelMapper = new ModelMapper();

    public static RestaurantDto of(Restaurant res_id) {
        return modelMapper.map(res_id, RestaurantDto.class);
    }

    public  static RestaurantDto restaurantDto(Restaurant restaurant){
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRes_id(restaurant.getRes_id());
        restaurant.setRes_name(restaurant.getRes_name());
        restaurant.setRes_district(restaurant.getRes_district());
        restaurant.setRes_lat(restaurant.getRes_lat());
        restaurant.setRes_lng(restaurant.getRes_lng());
        restaurant.setRes_address(restaurant.getRes_address());
        restaurant.setRes_phone(restaurant.getRes_phone());
        restaurant.setOperate_time(restaurant.getOperate_time());
        restaurant.setRes_menu(restaurant.getRes_menu());
        restaurant.setRes_image(restaurant.getRes_image());
        restaurant.setRes_thumbnail(restaurant.getRes_thumbnail());
        restaurant.setRes_intro(restaurant.getRes_intro());

        return restaurantDto;

    }
}