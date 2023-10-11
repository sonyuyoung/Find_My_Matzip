package com.matzip.controller;


import com.matzip.dto.BoardFormDto;
import com.matzip.dto.BoardSearchDto;
import com.matzip.entity.Board;
import com.matzip.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/admin/board/new")
    public String boardForm(Model model){
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "board/boardForm";
    }

    @PostMapping(value = "/admin/board/new")
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList){

        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }

        if(boardImgFileList.get(0).isEmpty() && boardFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 리뷰 이미지는 필수 입력 값 입니다.");
            return "board/boardForm";
        }

        try {
            System.out.println("boardFormDto 내용 확인: =======================================");
            System.out.println("boardFormDto 내용 확인: "+ boardFormDto.getBoard_title());
            System.out.println("boardFormDto 내용 확인: "+ boardFormDto.getContent());
            System.out.println("boardFormDto 내용 확인: "+ boardFormDto.getScore());
//            System.out.println("boardFormDto 내용 확인: "+ boardFormDto.getBoardImgDtoList().get(0).getImgName());
//            System.out.println("boardFormDto 내용 확인: "+ boardFormDto.getBoardImgDtoList().get(0).getImgUrl());
            System.out.println("boardFormDto 내용 확인: =======================================");
            boardService.saveBoard(boardFormDto, boardImgFileList);

        } catch (Exception e){
            model.addAttribute("errorMessage", "리뷰 등록 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/board/{boardId}")
    public String boardDtl(@PathVariable("boardId") Long boardId, Model model){

        try {
            BoardFormDto boardFormDto = boardService.getBoardDtl(boardId);
            model.addAttribute("boardFormDto", boardFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("boardFormDto", new BoardFormDto());
            return "board/boardForm";
        }

        return "board/boardForm";
    }

    @PostMapping(value = "/admin/board/{boardId}")
    public String boardUpdate(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                             @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }

        if(boardImgFileList.get(0).isEmpty() && boardFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "board/boardForm";
        }

        try {
            boardService.updateBoard(boardFormDto, boardImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/admin/boards", "/admin/boards/{page}"})
    public String boardManage(BoardSearchDto boardSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Board> boards = boardService.getAdminBoardPage(boardSearchDto, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDto", boardSearchDto);
        model.addAttribute("maxPage", 5);

        return "board/boardMng";
    }

    @GetMapping(value = "/board/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId){
        BoardFormDto boardFormDto = boardService.getBoardDtl(boardId);
        model.addAttribute("board", boardFormDto);
        return "board/boardDtl";
    }

}