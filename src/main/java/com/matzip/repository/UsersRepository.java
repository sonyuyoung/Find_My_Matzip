package com.matzip.repository;

import com.matzip.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUserid(String userid);
}
