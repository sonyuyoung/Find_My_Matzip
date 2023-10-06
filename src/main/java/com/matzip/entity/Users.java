package com.matzip.entity;

import com.matzip.constant.UserRole;

import com.matzip.dto.UsersFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class Users {

    @Id
    @Column(name="userid")
    private String userid;       //회원 id

    @Column(nullable = false)
    private String user_pwd; //회원 pw

    @Column(nullable = false)
    private String user_name; //회원 이름

    @Column(nullable = false)
    private String user_address; //주소

    @Column(nullable = false)
    private String user_phone; //전화번호



    @Enumerated(EnumType.STRING)
    private UserRole user_role; //역할(ADMIN,USER)

//    private LocalDateTime regTime; //가입 날짜
//
@Column(nullable = true)
private int user_following = 0; //팔로잉 수, set default value to 0



    @Column(nullable = true)
    private int user_followed = 0; //팔로워 수, set default value to 0



    public static Users createUsers(UsersFormDto usersFormDto, PasswordEncoder passwordEncoder){
        Users users = new Users();
        users.setUserid(usersFormDto.getUserid());
        String user_pwd = passwordEncoder.encode(usersFormDto.getUser_pwd());
        users.setUser_pwd(user_pwd);
        users.setUser_name(usersFormDto.getUser_name());
        users.setUser_address(usersFormDto.getUser_address());
        users.setUser_phone(usersFormDto.getUser_phone());
        users.setUser_role(UserRole.ADMIN);

        return users;
    }
}
