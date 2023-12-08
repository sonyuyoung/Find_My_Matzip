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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    public Long save(CommentDto commentDto) {
        Optional<Board> optionalBoard = boardRepository.findById(commentDto.getBoardId());
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            Comment comment = Comment.toSaveEntity(commentDto, board);
            return commentRepository.save(comment).getId();
        } else {
            throw new IllegalArgumentException("Board not found for id: " + commentDto.getBoardId());
        }
    }
    public List<CommentDto> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        List<Comment> commentEntityList = commentRepository.findAllByBoardOrderByIdDesc(board);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : commentEntityList) {
            CommentDto commentDto = CommentDto.toCommentDto(comment, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    public CommentDto findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return CommentDto.toCommentDto(comment, comment.getBoard().getId());
    }

    public List<CommentDto> findByBoardId(Long boardId) {
        List<Comment> comment = commentRepository.findByBoardIdOrderByCreationDateDesc(boardId);

        return comment.stream()
                .map(commentEntity -> CommentDto.toCommentDto(commentEntity, boardId))
                .collect(Collectors.toList());
    }


    // 수정 메서드
    public CommentDto update(Long id, CommentDto commentDto) throws Exception {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new Exception("Comment not found"));
        comment.update(commentDto);
        commentRepository.save(comment);
        return CommentDto.fromEntity(comment);
    }

    // 삭제 메서드
    public Long deleteComment(Long id) {
        Comment commentToDelete = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        Long itemId = commentToDelete.getBoard().getId(); // 해당 댓글의 상품 ID를 가져옵니다.
        commentRepository.delete(commentToDelete);

        return itemId; // 댓글이 삭제된 상품의 ID를 반환합니다.
    }

    // 페이징 메서드
    public Page<CommentDto> findCommentsByBoardId(Long boardId, Pageable pageable) {
        Page<Comment> commentEntitiesPage = commentRepository.findPageByBoardIdOrderByCreationDateDesc(boardId, pageable);
        return commentEntitiesPage.map(comment -> CommentDto.toCommentDto(comment, boardId));
    }




}
