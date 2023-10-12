package com.matzip.controller;

import com.matzip.dto.UsersFormDto;
import com.matzip.entity.Users;
import com.matzip.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

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
        UsersFormDto usersFormDto = usersService.findById(userid);

        model.addAttribute("usersFormDto", usersFormDto);
        return "users/modUsersForm";
    }

    //Users 업데이트
    @PostMapping(value = "/updateUsers")
    public String updateUsers(UsersFormDto usersFormDto, BindingResult bindingResult, Model model,
                              @RequestParam("userImgFile") MultipartFile userImgFile,
                              Principal principal) throws Exception {
        if(bindingResult.hasErrors()){
            System.out.println("error발생");
            return "users/modUsersForm";
        }

        try {
            //Users users = usersRepository.findByUserid(usersFormDto.getUserid());
            System.out.println("usersFormDto.getUserid()"+usersFormDto.getUserid());
            System.out.println("usersFormDto.getUser_name()"+usersFormDto.getUser_name());
            System.out.println("usersFormDto.getUser_address()"+usersFormDto.getUser_address());
            System.out.println("usersFormDto.getUser_phone()"+usersFormDto.getUser_phone());
            System.out.println("usersFormDto.getUser_image()"+usersFormDto.getUser_image());
            System.out.println("usersFormDto.getGender()"+usersFormDto.getGender());

            usersService.updateUsers(usersFormDto,userImgFile);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "users/modUsersForm";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //현재 로그인된 userDto(=pageUser)
        String myId = principal.getName();
        UsersFormDto myDto = usersService.findById(myId);

        model.addAttribute("myDto",myDto);
        model.addAttribute("pageUserDto",myDto);

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

    //내 프로필 조회
    @GetMapping(value = "/myProfile")
    public String myProfileForm(Principal principal,Model model){
        //myBoardList : 내 게시글 리스트
        /*List<BoardDto> myBoardList = boardService.getBoardList(principal.getName());
        model.addAttribute("myBoardList", myBoardList);*/


        //pageUser의 userDto
        String pageUserid = principal.getName();
        UsersFormDto pageUserDto = usersService.findById(pageUserid);

        //현재 로그인된 user의 userDto
        String myId = principal.getName();
        UsersFormDto myDto = usersService.findById(myId);

        //현재 로그인된 users 팔로워 리스트
       /* List<FollowDto> followDtoList = followService.getFollowDtoList(pageUserId,principal.getName());*/

        model.addAttribute("pageUserDto",pageUserDto);
        model.addAttribute("myDto",myDto);
        return "users/profileForm";
    }

    //다른사람 프로필 조회
   /* @GetMapping(value = "/profile")
    public String profileForm(Principal principal,Model model){
        //myBoardList : 내 게시글 리스트
       *//* List<BoardDto> myBoardList = boardService.getBoardList(principal.getName());
        model.addAttribute("myBoardList", myBoardList);*//*


        //현재 로그인된 users 객체 model에 추가
        String userid = principal.getName();
        Users users = usersRepository.findByUserid(userid);

        model.addAttribute("principal",principal);
        model.addAttribute("users",users);
        return "users/profileForm";
    }*/

    //pageUsers기준
    /*@GetMapping(value = "/profile/{userid}")
    public String profileForm(@PathVariable("userid") String pageUserId,Principal principal,Model model){
        //myBoardList : 내 게시글 리스트
        *//*List<BoardDto> myBoardList = boardService.getBoardList(principal.getName());
        model.addAttribute("myBoardList", myBoardList);*//*


        //현재 로그인된 user
        String userid = principal.getName();
        Users users = usersRepository.findByUserid(userid);

        //페이지의 user
        Users pageUsers = usersRepository

        model.addAttribute("principal",principal);
        model.addAttribute("users",users);
        return "users/profileForm";
    }*/

    @GetMapping("/users/")
    public String findAll(Model model){
        List<UsersFormDto> usersFormDtoList = usersService.findAll();
        model.addAttribute("usersList",usersFormDtoList);
        return "users/usersListForm";
    }

    @GetMapping("/delete/{userid}")
    public String deleteById(@PathVariable String userid){
        usersService.deleteById(userid);

        return "redirect:/users/";
    }


}
