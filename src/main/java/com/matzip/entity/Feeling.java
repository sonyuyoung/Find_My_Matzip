package com.matzip.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Table(name="follow",
        uniqueConstraints = {
        @UniqueConstraint(
                name="follow_uk",
                columnNames = {"to_user", "from_user"}
        )
})
@Entity
@Getter @Setter
public class Feeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private Users userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private Board board_id;

    @Column
    private Boolean good; //좋아요

    @Column
    private Boolean bad; //싫어요
    

    public Feeling(Users userid, Board board_id){
        this.userid = userid;
        this.board_id = board_id;

        //초기값 false
        this.good = false;
        this.bad = false;
    }
}