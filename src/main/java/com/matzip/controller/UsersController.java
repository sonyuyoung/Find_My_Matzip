package com.matzip.controller;

import com.matzip.dto.FollowDto;
import com.matzip.dto.UsersFormDto;
import com.matzip.entity.Users;
import com.matzip.service.BoardService;
import com.matzip.service.FollowService;
import com.matzip.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final FollowService followService;
    private final BoardService boardService;


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
    @GetMapping(value = {"/modUsersForm","/modUsersForm/{pageUserid}"})
    public String modUsersForm(@PathVariable(name ="pageUserid", required = false) String pageUserid,Principal principal,Model model){

        //내 프로필창에서 수정
        if(pageUserid == null){
            String userid = principal.getName();
            UsersFormDto usersFormDto = usersService.findById(userid);
            model.addAttribute("usersFormDto", usersFormDto);
        }
        //유저 리스트에서 수정
        else{
            UsersFormDto usersFormDto = usersService.findById(pageUserid);
            model.addAttribute("usersFormDto", usersFormDto);
        }

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
        String loginUserId = principal.getName();
        UsersFormDto loginUserDto = usersService.findById(loginUserId);

        model.addAttribute("loginUserDto",loginUserDto);
        model.addAttribute("pageUserDto",loginUserDto);

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
    @GetMapping(value = {"/profile","/profile/{pageUserid}"})
    public String myProfileForm(@PathVariable(name ="pageUserid", required = false) String pageUserId,Principal principal,Model model) throws Exception {
        //myBoardList : 내 게시글 리스트
       /* List<BoardDto> myBoardList = boardService.getBoardList(principal.getName());
        model.addAttribute("myBoardList", myBoardList);*/

        //마이페이지일때 ("/profile")
        if(pageUserId == null){
            //pageUser == principal
            pageUserId = principal.getName();
        }

        //pageUser의 userDto
        UsersFormDto pageUserDto = usersService.findById(pageUserId);

        //현재 로그인된 user의 userDto
        String loginUserId = principal.getName();
        UsersFormDto loginUserDto = usersService.findById(loginUserId);


        //팔로워 리스트
        List<FollowDto> followerDtoList = followService.getFollowerDtoList(pageUserId,principal.getName());
        //팔로잉 리스트
        List<FollowDto> followingDtoList = followService.getFollowingDtoList(pageUserId,principal.getName());

        //로그인 유저가 페이지 유저 팔로우 했는지 여부
        Boolean followcheck = followService.isFollow(pageUserId,principal.getName());

        //pageUser의 팔로잉수, 팔로워수
        int countFromUser = followService.countByFromUser(pageUserId);
        int countToUser = followService.countByToUser(pageUserId);

        //pageUser의 게시글 갯수 (boardService랑 boardController에 코드 추가 필요)
        /*int countBoard = boardService.countByUserId(pageUserId);*/

        /*model.addAttribute("countBoard", countBoard);*/
        model.addAttribute("countFromUser",countFromUser);
        model.addAttribute("countToUser",countToUser);
        model.addAttribute("followcheck",followcheck);
        model.addAttribute("followerDtoList",followerDtoList);
        model.addAttribute("followingDtoList",followingDtoList);
        model.addAttribute("pageUserDto",pageUserDto);
        model.addAttribute("loginUserDto",loginUserDto);
        return "users/profileForm";
    }

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

    @DeleteMapping("/deleteFollow/{toUserId}")
    public ResponseEntity<String> deleteFollow(@PathVariable String toUserId, Principal principal){
        followService.deleteFollow(toUserId,principal.getName());

        return ResponseEntity.ok().body("언팔로우 성공");
    }

    @GetMapping("/insertFollow/{toUserId}")
    public ResponseEntity<String> insertFollow(@PathVariable String toUserId,Principal principal){
        followService.insertFollow(toUserId,principal.getName());

        return ResponseEntity.ok().body("팔로잉 성공");
    }


}
