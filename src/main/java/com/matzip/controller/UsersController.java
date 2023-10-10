package com.matzip.controller;

import com.matzip.dto.UsersFormDto;
import com.matzip.entity.Users;
import com.matzip.repository.UsersRepository;
import com.matzip.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;


    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("usersFormDto", new UsersFormDto());
        return "users/usersForm";
    }

    @PostMapping(value = "/new")
    public String newUsers(@Valid UsersFormDto usersFormDto, BindingResult bindingResult, Model model,
                           @RequestParam("userImgFile") MultipartFile userImgFile){

        if(bindingResult.hasErrors()){
            return "users/usersForm";
        }

        try {
            Users users = Users.createUsers(usersFormDto, passwordEncoder);
            usersService.saveUsers(users,userImgFile);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "users/usersForm";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "users/usersLoginForm";
    }

    //modUsers폼 호출
    @GetMapping(value = "/modUsersForm")
    public String modUsersForm(Principal principal,Model model){
        String userid = principal.getName();
        Users users = usersRepository.findByUserid(userid);
        UsersFormDto usersFormDto = UsersFormDto.of(users);

        model.addAttribute("usersFormDto", usersFormDto);
        return "users/modUsersForm";
    }
    
    //Users 업데이트
    @PostMapping(value = "/updateUsers")
    public String updateUsers(@Valid UsersFormDto usersFormDto, BindingResult bindingResult, Model model,
                              @RequestParam("userImgFile") MultipartFile userImgFile) throws Exception {
        if(bindingResult.hasErrors()){
            return "users/modUsersForm";
        }

        try {
            //Users users = usersRepository.findByUserid(usersFormDto.getUserid());
            usersService.updateUsers(usersFormDto,userImgFile);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "users/modUsersForm";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "users/profileForm";
    }


    @GetMapping(value = "/login")
    public String loginMember(){
        return "users/usersLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "users/usersLoginForm";
    }

    @GetMapping(value = "/profile")
    public String profileForm(Principal principal,Model model){
        //myBoardList : 내 게시글 리스트
       /* List<BoardDto> myBoardList = boardService.getBoardList(principal.getName());
        model.addAttribute("myBoardList", myBoardList);*/


        //현재 로그인된 users 객체 model에 추가
        String userid = principal.getName();
        Users users = usersRepository.findByUserid(userid);

        model.addAttribute("users",users);
        return "users/profileForm";
    }
}
