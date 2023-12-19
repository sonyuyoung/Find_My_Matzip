package com.matzip.repository;

import com.matzip.entity.Board;
import com.matzip.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(Long commentId);

    List<Comment> findByBoardIdOrderByCreationDateDesc(Long boardId);

    Page<Comment> findPageByBoardIdOrderByCreationDateDesc(Long boardId, Pageable pageable);

    List<Comment> findByBoardIdAndParent_CommentIdOrderByCommentIdDesc(Long boardId, Long parentId);


}
