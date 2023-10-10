package com.matzip.controller;

import com.matzip.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;


    //게시글 작성 폼 띄우기
    @GetMapping(value = "/boardForm")
    public String boardForm(){

        return "/board/boardForm";
    }

    //게시글 작성
    @PostMapping(value = "/addNewBoard")
    public String addBoard(Model model){

        //model.addAttribute("boardFormDto",boardFormDto);
        //model.addAttribute("userid",userid);
        //model.addAttribute("res_id",res_id);
        return "/users/profile";
    }

}
