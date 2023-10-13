package com.matzip.dto;

import com.matzip.constant.BoardViewStatus;
import com.matzip.entity.Board;
import com.matzip.entity.Restaurant;
import com.matzip.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

//폼에서 입력한 내용(게시글 + 이미지) BoardFormDto에 담아서
// -> BoardController로 이동
@Getter @Setter
public class BoardFormDto {

    //1. 게시글(일반 데이터) 입력 부분

    //게시글아이디
    private Long id;

    //식당아이디
    private String resId;

    //유저아이디
    private Users user_id;

    //일단 에러나니까추가해봄
    private BoardViewStatus boardViewStatus;

    //게시글제목
    @NotBlank(message = "제목을 입력 바랍니다.")
    private String board_title;

    //리뷰상세내역
    private String content;

    //리뷰 평점
    @NotNull(message = "평점을 입력 바랍니다.")
    private Integer score;

    //2. 리뷰 이미지 입력 부분
    private List<BoardImgDto> boardImgDtoList = new ArrayList<>();

    private List<Long> boardImgIds = new ArrayList<>();

    //모델 매퍼 선언
    private static ModelMapper modelMapper = new ModelMapper();

    //폼 데이터 자동 매핑 -> Board객체 생성
/*    public Board createBoard(){
        Board board = modelMapper.map(this, Board.class);
        return board;
    }*/


    //board객체를 boardFormDto로 변환 : service에서 data 전달 시 이용
    public static BoardFormDto of(Board board){
        return modelMapper.map(board,BoardFormDto.class);
    }

}