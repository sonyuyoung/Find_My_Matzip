package com.matzip.dto;

import com.matzip.entity.Board;
import com.matzip.entity.Feeling;
import com.matzip.entity.Follow;
import com.matzip.entity.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeelingDto {

    private String id;
    private Board feelingBoard;
    private Users feelingUsers;

    //1 : 좋아요, -1 : 싫어요
    private int feel_num;



    //팔로잉
    public FeelingDto(String id, Board feelingBoard , Users feelingUsers,int feel_num) {
        this.id = id;
        this.feelingBoard =feelingBoard;
        this.feelingUsers = feelingUsers;
        this.feel_num = feel_num;
    }
}