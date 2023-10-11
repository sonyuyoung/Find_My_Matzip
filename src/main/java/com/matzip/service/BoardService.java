package com.matzip.service;



import com.matzip.dto.BoardFormDto;
import com.matzip.dto.BoardImgDto;
import com.matzip.dto.BoardSearchDto;
import com.matzip.dto.MainBoardDto;
import com.matzip.entity.Board;
import com.matzip.entity.BoardImg;
import com.matzip.repository.BoardImgRepository;
import com.matzip.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardImgService boardImgService;

    private final BoardImgRepository boardImgRepository;

    public Long saveBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFileList) throws Exception{

        System.out.println("여기서부터 오류발생 보드서비스,,, 세이브보드 시작");
        //상품 등록
        Board board = boardFormDto.createBoard();
        System.out.println("보드생성완료");
        boardRepository.save(board);
        System.out.println("보드저장완료");

        //이미지 등록
        for(int i=0;i<boardImgFileList.size();i++){
            BoardImg boardImg = new BoardImg();
            boardImg.setBoard(board);

            if(i == 0)
                boardImg.setRepimgYn("Y");
            else
                boardImg.setRepimgYn("N");

            boardImgService.saveBoardImg(boardImg, boardImgFileList.get(i));
        }

        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardFormDto getBoardDtl(Long boardId){
        List<BoardImg> boardImgList = boardImgRepository.findByBoardIdOrderByIdAsc(boardId);
        List<BoardImgDto> boardImgDtoList = new ArrayList<>();
        for (BoardImg boardImg : boardImgList) {
            BoardImgDto boardImgDto = BoardImgDto.of(boardImg);
            boardImgDtoList.add(boardImgDto);
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        BoardFormDto boardFormDto = BoardFormDto.of(board);
        boardFormDto.setBoardImgDtoList(boardImgDtoList);
        return boardFormDto;
    }

    // 상품 수정을  처리하는 로직.
    public Long updateBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFileList) throws Exception{
        //상품 수정,
        // 기존 아이템 정보를 불러오기.
        Board board = boardRepository.findById(boardFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        // 기존 아이템에 내용에 , 더티 체킹. 변경사항에 대해서, 영속성이 알아서 자동으로 처리.
        board.updateBoard(boardFormDto);
        List<Long> boardImgIds = boardFormDto.getBoardImgIds();

        //이미지 등록
        for(int i=0;i<boardImgFileList.size();i++){
            boardImgService.updateBoardImg(boardImgIds.get(i),
                    boardImgFileList.get(i));
        }

        return board.getId();
    }

    @Transactional(readOnly = true)
    public Page<Board> getAdminBoardPage(BoardSearchDto boardSearchDto, Pageable pageable){
        return boardRepository.getAdminBoardPage(boardSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainBoardDto> getMainBoardPage(BoardSearchDto boardSearchDto, Pageable pageable){
        return boardRepository.getMainBoardPage(boardSearchDto, pageable);
    }

}