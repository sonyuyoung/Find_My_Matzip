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

import static com.matzip.entity.Users.createUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class UsersServiceTest {

    @Autowired
    UsersService usersService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Users createUsers() {
        UsersFormDto usersFormDto = new UsersFormDto();
        usersFormDto.setUserid("john_doe");
        usersFormDto.setUser_name("John Doe");
        usersFormDto.setUser_pwd("password123");
        usersFormDto.setUser_address("123 Main Street, City");
        usersFormDto.setUser_phone("123-456-7890");
        return Users.createUsers(usersFormDto, passwordEncoder);

    }
@Test
    @DisplayName("회원가입테스트")
    public void saveUserTest(){
    Users users = createUsers();
    Users saveUsers = usersService.saveUsers(users);
    System.out.println("users.getUserid(): "+users.getUserid());
    System.out.println("users.getUser_pwd(): "+users.getUser_pwd());
    System.out.println("saveUsers(): "+saveUsers.getUserid());
    assertEquals(users.getUserid(),saveUsers.getUserid());
    assertEquals(users.getUser_name(),saveUsers.getUser_name());
    assertEquals(users.getUser_pwd(),saveUsers.getUser_pwd());
    assertEquals(users.getUser_address(),saveUsers.getUser_address());
    assertEquals(users.getUser_phone(),saveUsers.getUser_phone());
    assertEquals(users.getUser_role(),saveUsers.getUser_role());


}

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest(){
        Users user1 = createUsers();
        Users user2 = createUsers();
        usersService.saveUsers(user1);
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            usersService.saveUsers(user2);});
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}