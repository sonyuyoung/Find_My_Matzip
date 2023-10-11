package com.matzip.dto;

import com.matzip.constant.BoardViewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardSearchDto {

    private String searchDateType;

    private BoardViewStatus searchViewStatus;

    private String searchBy;

    private String searchQuery = "";

}