package com.example.springboot.security.repository;

import com.example.springboot.security.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<UserAccount, Long> {}
