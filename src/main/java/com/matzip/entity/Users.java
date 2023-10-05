package com.matzip.entity;

import com.matzip.constant.UserRole;

import com.matzip.dto.UsersFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class Users {

    @Id
    @Column(name="user_id")
    private String userid;       //회원 id

    @Column(nullable = false, length = 50)
    private String user_pwd; //회원 pw

    @Column(nullable = false)
    private String user_name; //회원 이름

    @Column(nullable = false)
    private LocalDateTime user_birth; //생년월일

    @Column(nullable = false)
    private String user_address; //주소

    @Column(nullable = true)
    private String user_image; //프로필 사진

    @Column(nullable = true)
    private int user_level; //신뢰 레벨


    @Enumerated(EnumType.STRING)
    private UserRole user_role; //역할(ADMIN,USER)

    private LocalDateTime regTime; //가입 날짜

    @Column(nullable = false)
    private String user_phone; //전화번호

    @Column(nullable = false)
    private boolean user_sex; //성별

    public boolean isUser_sex() {
        return user_sex;
    }

    @Column(nullable = true)
    private int user_following; //팔로잉 수

    @Column(nullable = true)
    private int user_followed; //팔로워 수


    public static Users createUsers(UsersFormDto usersFormDto, PasswordEncoder passwordEncoder){
        Users users = new Users();
        users.setUserid(usersFormDto.getUserid());
        String user_pwd = passwordEncoder.encode(usersFormDto.getUser_pwd());
        users.setUser_pwd(user_pwd);
        users.setUser_name(usersFormDto.getUser_name());
        users.setUser_birth(usersFormDto.getUser_birth());
        users.setUser_address(usersFormDto.getUser_address());
        users.setUser_image(usersFormDto.getUser_image());
        users.setUser_phone(usersFormDto.getUser_phone());
        users.setUser_sex(usersFormDto.isUser_sex());
        users.setUser_role(UserRole.ADMIN);

        return users;
    }
}

