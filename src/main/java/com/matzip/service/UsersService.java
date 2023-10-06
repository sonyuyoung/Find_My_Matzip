package com.matzip.service;


import com.matzip.entity.Users;
import com.matzip.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        Users users = usersRepository.findByUserid(userid);

        if(users == null){
            throw new UsernameNotFoundException(userid);
        }

        return User.builder()
                .username(users.getUserid())
                .password(users.getUser_pwd())
                .roles(users.getUser_role().toString())
                .build();
    }


}
