package com.matzip.repository;


import com.matzip.entity.Feeling;
import com.matzip.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeelingRepository extends JpaRepository<Feeling, Long>{

     //팔로잉 리스트 호출
     @Query("select f " +
             "from Feeling f " +
             "where f.feelingBoard.id = :boardId and f.feelingUsers.userid = :usersId")
     Feeling fingByUsersIdAndBoardId(@Param("boardId") Long boardId,@Param("usersId") String usersId);

     //좋아요 or 싫어요 수
     //feel_num -> 1:좋아요, -1:싫어요
     @Query("select count(f) " +
             "from Feeling f " +
             "where f.feelingBoard.id = :boardId and f.feel_num = :feel_num")
     int countFeeling(@Param("boardId") Long boardId,@Param("feel_num") int feel_num);








}