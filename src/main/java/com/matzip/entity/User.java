package com.matzip.entity;

import com.matzip.constant.UserRole;
import com.matzip.constant.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="user")
@Getter
@Setter
@ToString
public class User{
    @Id
    @Column(name="user_id")
    private String user_id;       //회원 id

    @Column(nullable = false, length = 50)
    private String user_pwd; //회원 pw

    @Column(nullable = false)
    private String user_name; //회원 이름

    @Column(nullable = false)
    private LocalDateTime user_birth; //생년월일

    @Column(nullable = false)
    private String user_address; //주소

    @Column(nullable = false)
    private String user_image; //프로필 사진

    @Column(nullable = false)
    private int user_level; //신뢰 레벨

    @Enumerated(EnumType.STRING)
    private UserRole user_role; //역할(ADMIN,USER)

    private LocalDateTime regTime; //가입 날짜

    @Column(nullable = false)
    private String user_phone; //전화번호

    @Column(nullable = false)
    private boolean user_sex; //성별

    @Column(nullable = false)
    private int user_following; //팔로잉 수

    @Column(nullable = false)
    private int user_followed; //팔로워 수
}
