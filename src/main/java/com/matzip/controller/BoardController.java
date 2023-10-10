package com.matzip.controller;

import com.matzip.entity.Users;
import com.matzip.repository.BoardRepository;
import com.matzip.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;


    //게시글 작성 폼 띄우기
    @GetMapping(value = "/boardForm")
    public String boardForm(Principal principal,Model model){
        String userid = principal.getName(); //회원 id 받기
        Users users = usersRepository.findByUserid(userid);
        model.addAttribute("users",users);
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
