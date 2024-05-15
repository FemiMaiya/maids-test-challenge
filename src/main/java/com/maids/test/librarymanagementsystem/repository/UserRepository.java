package com.maids.test.librarymanagementsystem.repository;

import com.maids.test.librarymanagementsystem.entity.securityConfig.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);
}
