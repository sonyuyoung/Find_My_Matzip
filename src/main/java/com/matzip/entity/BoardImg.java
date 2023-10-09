package com.matzip.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="board_img")
@Getter @Setter
public class BoardImg{

    @Id
    @Column(name="board_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long board_img_id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    private String repimgYn; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_No")
    private Board board;

    public void updateBoardImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}