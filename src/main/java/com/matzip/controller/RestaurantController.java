package com.matzip.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;



@Controller
@RequiredArgsConstructor
public class RestaurantController {

   /* private final BoardService boardService;
    @GetMapping(value = "/maps")
    public String main(BoardSearchDto boardSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainBoardDto> boards = boardService.getMainBoardPage(boardSearchDto, pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDto", boardSearchDto);
        model.addAttribute("maxPage", 5);
        return "main";
    }
*/
}