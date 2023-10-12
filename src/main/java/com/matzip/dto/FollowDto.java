package com.matzip.dto;

import com.matzip.entity.Follow;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDto {

    //Follower 정보
    private String id;
    private String name;
    private String profileImage;

    //(로그인한 유저A가) ->  (pageUsers의 팔로워를) 본인이 팔로우 했는지 여부 체크
    private boolean  subscribeState;

    public FollowDto(Follow follow) {
        this.id = follow.getFromUser().getUserid();
        this.name = follow.getFromUser().getUser_name();
        this.profileImage = follow.getFromUser().getUser_image();
        this.subscribeState = false;
    }

}