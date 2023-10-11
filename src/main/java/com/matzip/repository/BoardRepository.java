package com.matzip.repository;

import com.matzip.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{

    Board findByBoardNo(Long boardNo);

}