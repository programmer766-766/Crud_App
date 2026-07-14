package com.crud.application.repository;

import com.crud.application.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Boolean existsByName(String name);
    UserEntity findByName(String name);
    Optional<UserEntity> findByUsername(String username);

//    //JPQL QUERY for fetch user along with Pan details
//    @Query(value = "SELECT u FROM UserEntity u JOIN FETCH u.panId WHERE u.userId=:userId")
//    UserEntity fetchUserWithPan(@Param("userId") int userId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE PanEntity p SET p.email=:email WHERE p.userId.userId=:userId")
    int updatePanEmail(@Param("userId")int userId,@Param("email")String email);
}
