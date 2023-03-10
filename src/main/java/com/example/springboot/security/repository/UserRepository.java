package com.example.springboot.security.repository;

import com.example.springboot.security.entity.UserAccount;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserAccount, Long> {

    UserAccount findByUsername(String username);

}
