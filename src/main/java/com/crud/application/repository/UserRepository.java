package com.crud.application.repository;

import com.crud.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Boolean existsByName(String name);
    UserEntity findByName(String name);
}
