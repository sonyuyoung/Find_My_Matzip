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
    @Column(name="res_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long res_id;       //식당 번호

    @Column(nullable = false)
    private String res_name;//식당 이름

    @Column(nullable = false)
    private String res_address;//식당 주소

    @Column
    private String res_image; //식당 사진

    /*@Column(nullable = false)
    private String avg_score; //평균 평점*/

}