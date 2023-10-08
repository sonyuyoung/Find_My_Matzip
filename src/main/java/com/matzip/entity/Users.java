package com.matzip.entity;

import com.matzip.constant.UserRole;
import com.matzip.dto.UsersFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


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

    private String user_address; //주소

    private String user_phone; //전화번호

    @Enumerated(EnumType.STRING)
    private UserRole user_role; //역할(ADMIN,USER)

    private String user_image; //프로필 이미지

    //private LocalDateTime regTime; //가입 날짜





    public static Users createUsers(UsersFormDto usersFormDto, PasswordEncoder passwordEncoder){
        Users users = new Users();
        users.setUserid(usersFormDto.getUserid());
        String user_pwd = passwordEncoder.encode(usersFormDto.getUser_pwd());
        users.setUser_pwd(user_pwd);
        users.setUser_name(usersFormDto.getUser_name());
        users.setUser_address(usersFormDto.getUser_address());
        users.setUser_phone(usersFormDto.getUser_phone());
        users.setUser_role(UserRole.ADMIN);
        users.setUser_image(usersFormDto.getUser_image());
        return users;
    }
}
