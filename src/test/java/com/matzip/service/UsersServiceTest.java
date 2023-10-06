package com.matzip.service;


import com.matzip.dto.UsersFormDto;
import com.matzip.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.matzip.entity.Users.createUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class UsersServiceTest {
    @Autowired
    UsersService usersService;

    @Autowired
    PasswordEncoder passwordEncoder;
//
//    public void testCreateUsers() {
//        UsersFormDto usersFormDto = new UsersFormDto();
//        usersFormDto.setUserid("john_doe");
//        usersFormDto.setUser_name("John Doe");
//        usersFormDto.setUser_pwd("password123");
//        usersFormDto.setUser_birth(LocalDateTime.of(1990, 5, 15, 0, 0));
//        usersFormDto.setUser_address("123 Main Street, City");
//        usersFormDto.setUser_phone("123-456-7890");
//        usersFormDto.setUser_image("profile.jpg");
////        usersFormDto.getGender();// Assuming true is for male
//    }
}