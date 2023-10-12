package com.matzip.service;

import com.matzip.dto.FollowDto;
import com.matzip.entity.Follow;
import com.matzip.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    //팔로워dto 리스트
    public List<FollowDto> getFollowerDtoList(String toUserId, String loginUserId) throws Exception{
        //pageuser를 팔로잉한 사람 목록(FollowerDto : id,name,profileImage,subscribeState)
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
        //pageuser가 팔로잉한 사람 목록(FollowerDto : id,name,profileImage,subscribeState)
        List<Follow> fromUserList = followRepository.findByFromUserId(fromUserId);

        //DTO로 변환
        List<FollowDto> followingDtoList = new ArrayList<>();
        for(Follow follow:fromUserList){
            FollowDto followDto = new FollowDto(follow.getToUser().getUserid(), follow.getToUser().getUser_name(), follow.getToUser().getUser_image());
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

    //로그인 유저가 페이지 유저 팔로잉했는지 여부 호출
    public Boolean isFollow(String toUserId, String loginUserId) throws Exception{
        Follow followCheck = followRepository.findByToUserIdAndFromUserId(toUserId, loginUserId);

        if(followCheck != null){
            return true;
        }
        else{
            return  false;
        }

    }

    public void deleteFollow(String toUserId,String fromUserId) {
       Follow follow = followRepository.findByToUserIdAndFromUserId(toUserId,fromUserId);
       followRepository.delete(follow);

    }


}