package com.crud.application.repository;

import com.crud.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Boolean existsByName(String name);
    Optional<UserEntity> findByName(String name);
}
