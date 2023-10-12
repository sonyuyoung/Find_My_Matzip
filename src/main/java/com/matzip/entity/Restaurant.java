package com.matzip.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="restaurant")
@Getter
@Setter
@ToString
public class Restaurant {

    @Id
    @Column(name="res_id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String res_id;       //식당 id

    @Column(nullable = false)
    private String res_name;//식당 이름

    @Column(nullable = false)
    private String res_district;//구군

    @Column(nullable = false)
    private String res_lat;//위도

    @Column(nullable = false)
    private String res_lng;//경도

    @Column(nullable = false)
    private String res_address;//식당 주소

    @Column(nullable = false)
    private String res_phone;//연락처

    @Column(nullable = false)
    private String operate_time;//운영 및 시간

    @Column(nullable = false)
    private String res_menu;//대표 메뉴

    @Column
    private String res_image; //식당 이미지 url

    @Column
    private String res_thumbnail; //썸네일 이미지 url

    @Column
    private String res_intro; //가게 소개

    /*@Column(nullable = false)
    private String avg_score; //평균 평점*/

}