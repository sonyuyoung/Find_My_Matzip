package com.matzip.controller;


import com.matzip.dto.UsersFormDto;
import com.matzip.entity.Users;
import com.matzip.service.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;



    @SpringBootTest
    @AutoConfigureMockMvc
    @Transactional
    @TestPropertySource(locations="classpath:application-test.properties")
    class UsersControllerTest {

        @Autowired
        private UsersService usersService;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        PasswordEncoder passwordEncoder;


        public Users createUsers(String userid, String user_pwd) {
            UsersFormDto usersFormDto = new UsersFormDto();
            usersFormDto.setUserid("john_doe");
            usersFormDto.setUser_name("John Doe");

            usersFormDto.setUser_address("123 Main Street, City");
            usersFormDto.setUser_phone("123-456-7890");
            usersFormDto.setUser_pwd(user_pwd);
            Users users = Users.createUsers(usersFormDto, passwordEncoder);
            return usersService.saveUsers(users);

        }

        @Test
        @DisplayName("로그인 성공 테스트")
        public void loginSuccessTest() throws Exception {
            String userid = "test";
            String user_pwd = "1234";
            this.createUsers(userid, user_pwd);
            mockMvc.perform(formLogin().userParameter("userid")
                            .loginProcessingUrl("/users/login")
                            .user(userid).password(user_pwd))
                    .andExpect(SecurityMockMvcResultMatchers.authenticated());
        }

    }
//        @Test
//        @DisplayName("로그인 실패 테스트")
//        public void loginFailTest() throws Exception{
//            String userid = "test";
//            String user_pwd = "1234";
//            this.createUsers(userid, user_pwd);
//            mockMvc.perform(formLogin().userParameter("userid")
//                            .loginProcessingUrl("/users/login")
//                            .user(userid).password("12345"))
//                    .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
//        }

