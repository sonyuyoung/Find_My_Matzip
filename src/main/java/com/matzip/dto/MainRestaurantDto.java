package com.matzip.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainRestaurantDto {

    private String res_id;       //식당 id

    private String res_name;//식당 이름

    private String res_thumbnail; //썸네일 이미지 url

    private String res_menu;//대표 메뉴



    @QueryProjection
    public MainRestaurantDto(String res_id, String res_name, String res_thumbnail,String res_menu){
        this.res_id = res_id;
        this.res_name = res_name;
        this.res_thumbnail = res_thumbnail;
        this.res_menu = res_menu;
    }

}