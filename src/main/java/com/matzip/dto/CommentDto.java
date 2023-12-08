package com.matzip.dto;

import com.matzip.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime commentCreatedTime;
    private Long boardId;

    public CommentDto() {

    }


    public static CommentDto toCommentDto(Comment comment, Long boardId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentWriter(comment.getCommentWriter());
        commentDto.setCommentContents(comment.getCommentContents());
        commentDto.setCommentCreatedTime(comment.getRegTime());
        commentDto.setBoardId(boardId);
        return commentDto;
    }

    public static CommentDto fromEntity(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentWriter(comment.getCommentWriter());
        commentDto.setCommentContents(comment.getCommentContents());
        commentDto.setBoardId(comment.getBoard().getId());
        commentDto.setCommentCreatedTime(comment.getRegTime());
        return commentDto;
    }
    public CommentDto(String commentWriter, String commentContents, Long boardId) {
        this.commentWriter = Objects.requireNonNull(commentWriter, "commentWriter must not be null");
        this.commentContents = Objects.requireNonNull(commentContents, "commentContents must not be null");
        this.boardId = Objects.requireNonNull(boardId, "boardId must not be null");
    }
}
