package com.matzip.entity;

import com.matzip.constant.BoardViewStatus;
import com.matzip.dto.BoardFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="board")
@Getter
@Setter
@ToString
public class Board extends BaseEntity{

    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       //게시글 번호

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "res_id")
//    private Restaurant restaurant;//식당
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    private Users user; //회원

    @Column(nullable = false)
    private String board_title; //제목

    @Column
    private String content ; //내용

    @Column(nullable = false)
    private int score; //평점

    @Enumerated(EnumType.STRING)
    private BoardViewStatus boardViewStatus; //게시글 상태

    /* @Column(nullable = false)
    private String writeDate; //게시 일자*/


    public void updateBoard(BoardFormDto boardFormDto){
        this.board_title = boardFormDto.getBoard_title();
        this.content = boardFormDto.getContent();
        this.score = boardFormDto.getScore();
        this.boardViewStatus = boardFormDto.getBoardViewStatus();
    }

}