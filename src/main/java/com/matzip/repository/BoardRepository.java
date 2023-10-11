package com.matzip.repository;


import com.matzip.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>,
        QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {

    //테스트용 코드같은데... 아닌가 ...

//    List<Board> findByBoard_title(String board_title);
//
//    List<Board> findByItemNmOrItemDetail(String itemNm, String itemDetail);
//
//    List<Board> findByPriceLessThan(Integer price);
//
//    List<Board> findByPriceLessThanOrderByPriceDesc(Integer price);
//
//    @Query("select i from Item i where i.itemDetail like " +
//            "%:itemDetail% order by i.price desc")
//    List<Board> findByItemDetail(@Param("itemDetail") String itemDetail);
//
//    @Query(value="select * from item i where i.item_detail like " +
//            "%:itemDetail% order by i.price desc", nativeQuery = true)
//    List<Board> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}