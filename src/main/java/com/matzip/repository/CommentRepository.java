package com.matzip.repository;

import com.matzip.entity.Board;
import com.matzip.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    Optional<Comment> findById(Long commentId);

    // select * from comment_table where board_id=? order by id desc;
    List<Comment> findAllByBoardOrderByIdDesc(Board board);

    List<Comment> findByBoardIdOrderByCreationDateDesc(Long boardId);

    Page<Comment> findPageByBoardIdOrderByCreationDateDesc(Long boardId, Pageable pageable);
}
