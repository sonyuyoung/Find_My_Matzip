package com.matzip.dto;

import com.matzip.constant.UserRole;
import com.matzip.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class UsersFormDto {
//
//    @NotBlank(message = "id는 필수 입력 값입니다.")
//    private String user_id;
//
//    @NotBlank(message = "이름은 필수 입력 값입니다.")
//    private String user_name; //회원 이름
//
//
//    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
//    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
//    private String user_pwd; //회원 pw
//
//    @NotEmpty(message = "생일은 필수 입력 값입니다.")
//    private LocalDateTime user_birth;
//
//
//    @NotEmpty(message = "주소는 필수 입력 값입니다.")
//    private String user_address; //주소
//
//    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
//    private String user_phone; //전화번호
//
//    @NotEmpty(message = "성별은 필수 입력 값입니다.")
//    private boolean user_sex; //성별
//
//
//
//    private String user_image; //프로필 사진
//
//    public boolean isUser_sex() {
//        return user_sex;
//    }


    private String userid;
    private String user_pwd;
    private String user_name;
    private String user_address;
    private UserRole user_role;
    private String user_phone;
    private String user_image;
//    private LocalDateTime user_birth;
//    private int user_level;
//    private boolean user_sex;

    private static ModelMapper modelMapper = new ModelMapper();

    public Users createUsers(){
        return modelMapper.map(this, Users.class);
    }

    public static UsersFormDto of(Users users){
        return modelMapper.map(users,UsersFormDto.class);
    }


}
