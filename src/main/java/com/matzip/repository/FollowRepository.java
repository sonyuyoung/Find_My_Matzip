package com.matzip.repository;


import com.matzip.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long>{

     //팔로잉 리스트 호출
     @Query("select f from Follow f where f.fromUser.userid = :fromUserId")
     List<Follow> findByFromUserId(@Param("fromUserId") String fromUserId);


     //팔로워 리스트 호출
     @Query("select f from Follow f where f.toUser.userid = :toUserId")
     List<Follow> findByToUserId(@Param("toUserId") String toUserId);

}