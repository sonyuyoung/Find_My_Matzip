package com.matzip.entity;

import com.matzip.dto.FeelingDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@NoArgsConstructor
@Table(name = "feeling")
@Entity
@Getter @Setter
public class Feeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board feelingBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private Users feelingUsers;

    @Column
    private int feel_num; //1 : 좋아요, -1 : 싫어요

    //Feeling -> FeelingDto로 형 변환
//    private static ModelMapper modelMapper = new ModelMapper();
//    public static FeelingDto of(Feeling feeling){
//        return modelMapper.map(feeling, FeelingDto.class);
//    }


    public Feeling(Board feelingBoard, Users feelingUsers, int feel_num) {
        this.feelingBoard = feelingBoard;
        this.feelingUsers = feelingUsers;;
        this.feel_num = feel_num;
    }

    public void updateFeeling(int feel_num){
        this.feel_num = feel_num;
    }
}