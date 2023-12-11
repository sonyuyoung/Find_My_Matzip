package com.matzip.entity;


import com.matzip.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = true)
    private String commentWriter;

    @Column
    private String commentContents;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }


    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;



    public static Comment toSaveEntity(CommentDto commentDto, Board board) {
        Comment comment = new Comment();
        comment.setCommentWriter(commentDto.getCommentWriter());
        comment.setCommentContents(commentDto.getCommentContents());
        comment.setBoard(board);
        return comment;
    }

    public void update(CommentDto commentDto) {
        // 필요한 필드를 업데이트하세요.
        this.commentContents = commentDto.getCommentContents();
    }
}

