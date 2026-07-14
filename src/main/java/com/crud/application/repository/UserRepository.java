package com.crud.application.repository;

import com.crud.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Boolean existsByName(String name);
    UserEntity findByName(String name);
    Optional<UserEntity> findByUsername(String username);

    //JPQL QUERY for fetch user along with Pan details
    @Query(value = "SELECT u FROM UserEntity u JOIN FETCH u.panId WHERE u.userId=:userId")
    UserEntity fetchUserWithPan(@Param("userId") int userId);
}
