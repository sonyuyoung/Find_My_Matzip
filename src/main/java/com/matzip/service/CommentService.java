package com.matzip.service;

import com.matzip.dto.CommentDto;
import com.matzip.entity.Board;
import com.matzip.entity.Comment;
import com.matzip.repository.BoardRepository;
import com.matzip.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // save 메서드는 주어진 CommentDto 객체의 parentId를 확인하여
    // 부모 댓글의 유무에 따라 처리를 분기하고, 새로운 댓글을 저장하거나 대댓글을 저장하는 기능을 담당
    @Transactional
    public Long save(CommentDto commentDto) {
        if (commentDto.getParentId() != null) {
            // 부모 댓글이 있는 경우 대댓글 저장 로직 호출
            return saveReply(commentDto);
        } else {
            // 부모 댓글이 없는 경우 새로운 댓글 저장 로직 호출
            return saveComment(commentDto);
        }
    }

    //saveComment() 메서드는 새로운 댓글을 저장하는 로직을 유지
    private Long saveComment(CommentDto commentDto) {
        Board board = boardRepository.findById(commentDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found for id: " + commentDto.getBoardId()));

        Comment comment = Comment.toSaveEntity(commentDto, board, null);
        return commentRepository.save(comment).getCommentId();
    }

    //saveReply() 메서드는 부모 댓글이 있는 경우 호출 대댓글 저장에 관련된 로직을 담당
    public Long saveReply(CommentDto commentDto) {
        Board board = boardRepository.findById(commentDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found for id: " + commentDto.getBoardId()));

        Comment parentComment = commentRepository.findById(commentDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("Parent comment not found for id: " + commentDto.getParentId()));

        Comment replyComment = Comment.toSaveEntity(commentDto, board, parentComment);
        return commentRepository.save(replyComment).getCommentId();
    }


    public CommentDto findByCommentId(CommentDto commentDto ,Long commentId, Long boardId) {
        Optional<Comment> commentOptional = commentRepository.findByCommentId(commentId);

        if (commentOptional.isPresent()) {
            //개별 댓글을 조회할 때는 부모 댓글이 없기 때문
            commentDto = CommentDto.toCommentDto(commentOptional.get(), boardId,null);
            return commentDto;
        } else {

            return new CommentDto();
        }
    }


    public Comment findCommentById(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElse(null);
    }

    public CommentDto findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return CommentDto.toCommentDto(comment, comment.getBoard().getId(),null);
    }

    public List<CommentDto> findByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardIdOrderByCreationDateDesc(boardId);

        return comments.stream()
                .map((Comment commentEntity) -> CommentDto.toCommentDto(commentEntity, boardId,null))
                .collect(Collectors.toList());
    }


    public CommentDto findById(Long commentId, Long boardId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return CommentDto.toCommentDto(comment, boardId,null);
    }

    public List<CommentDto> findByBoardId(Long boardId, Long parentId) {
        List<Comment> commentList = commentRepository.findByBoardIdAndParent_CommentIdOrderByCommentIdDesc(boardId, parentId);

        return commentList.stream()
                .map(commentEntity -> CommentDto.toCommentDto(commentEntity, boardId,parentId))
                .collect(Collectors.toList());
    }


    public CommentDto update(Long commentId, CommentDto commentDto, Long parentCommentId) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception("Comment not found"));

        Comment parentComment = null;
        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        }

        comment.update(commentDto);
        commentRepository.save(comment);
        return CommentDto.fromEntity(comment);
    }

    // 삭제 메서드
    public Long deleteComment(Long commentId) {
        Comment commentToDelete = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        Long itemId = commentToDelete.getBoard().getId(); // 해당 댓글의 상품 ID를 가져옵니다.
        commentRepository.delete(commentToDelete);

        return itemId; // 댓글이 삭제된 상품의 ID를 반환합니다.
    }

    // 페이징 메서드
    public Page<CommentDto> findCommentsByBoardId(Long boardId, Pageable pageable) {
        Page<Comment> commentEntitiesPage = commentRepository.findPageByBoardIdOrderByCreationDateDesc(boardId, pageable);
        return commentEntitiesPage.map(comment -> CommentDto.toCommentDto(comment, boardId,null));
    }



}
