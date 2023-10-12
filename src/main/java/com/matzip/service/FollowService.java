package com.matzip.service;

import com.matzip.dto.FollowDto;
import com.matzip.entity.Follow;
import com.matzip.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    //팔로워dto 리스트
    public List<FollowDto> getFollowerDtoList(String toUserId, String loginUserId) throws Exception{
        //pageuser를 팔로잉한 사람 목록(FollowDto : id,name,profileImage,subscribeState)
        List<Follow> toUserList = followRepository.findByToUserId(toUserId);
        
        //DTO로 변환
        List<FollowDto> followerDtoList = new ArrayList<>();
        for(Follow follow:toUserList){
            FollowDto followDto = new FollowDto(follow);
            followerDtoList.add(followDto);
        }

        //내가 팔로잉한 사람 목록(Follow : id,toUser,fromUser)
        List<Follow> loginUserList = followRepository.findByFromUserId(loginUserId);

        //팔로잉 여부는 나중에 저장
        for(Follow follow : loginUserList) {
            for(FollowDto dto : followerDtoList) {
                if(Objects.equals(follow.getToUser().getUserid(), dto.getId()))
                    dto.setSubscribeState(true);
            }

        }

        return followerDtoList;
    }

    //팔로잉dto 리스트
    public List<FollowDto> getFollowingDtoList(String fromUserId, String loginUserId) throws Exception{
        //pageuser가 팔로잉한 사람 목록(FollowDto : id,name,profileImage,subscribeState)
        List<Follow> fromUserList = followRepository.findByFromUserId(fromUserId);

        //DTO로 변환
        List<FollowDto> followingDtoList = new ArrayList<>();
        for(Follow follow:fromUserList){
            FollowDto followDto = new FollowDto(follow);
            followingDtoList.add(followDto);
        }

        //내가 팔로잉한 사람 목록(Follow : id,toUser,fromUser)
        List<Follow> loginUserList = followRepository.findByFromUserId(loginUserId);

        //팔로잉 여부는 나중에 저장
        for(Follow follow : loginUserList) {
            for(FollowDto dto : followingDtoList) {
                if(Objects.equals(follow.getToUser().getUserid(), dto.getId()))
                    dto.setSubscribeState(true);
            }

        }

        return followingDtoList;
    }

}