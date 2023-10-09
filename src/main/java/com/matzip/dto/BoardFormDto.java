package com.matzip.dto;

import com.matzip.entity.Board;
import com.matzip.entity.Restaurant;
import com.matzip.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

//폼에서 입력한 내용(게시글 + 이미지) BoardFormDto에 담아서
// -> BoardController로 이동
@Getter @Setter
public class BoardFormDto {

    //1. 게시글(일반 데이터) 입력 부분
    private Long board_No;

    private Restaurant res_id;

    private Users user_id;

    @NotBlank(message = "제목을 입력 바랍니다.")
    private String board_title;

    private String content;

    @NotBlank(message = "평점을 입력 바랍니다.")
    private int score;

    //2. 이미지 입력 부분
    private List<BoardImgDto> boardImgDtoList = new ArrayList<>();

    private List<Long> boardImgIds = new ArrayList<>();

    //모델 매퍼 선언
    private static ModelMapper modelMapper = new ModelMapper();

    //폼 데이터 자동 매핑 -> Board객체 생성
    public Board createBoard(){
        return modelMapper.map(this, Board.class);
    }

    //board객체를 boardFormDto로 변환 : service에서 data 전달 시 이용
    public static BoardFormDto of(Board board){
        return modelMapper.map(board,BoardFormDto.class);
    }

}