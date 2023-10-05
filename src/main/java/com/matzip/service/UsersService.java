package com.matzip.service;


import com.matzip.entity.Users;
import com.matzip.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Users saveUsers(Users users) {
        validateDuplicateUsers(users);
        return usersRepository.save(users);
    }

    private void validateDuplicateUsers(Users users) {
        Users findUsers = usersRepository.findByUserid(users.getUserid());
        if (findUsers != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
