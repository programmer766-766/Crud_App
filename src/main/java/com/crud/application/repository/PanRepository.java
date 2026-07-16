package com.crud.application.repository;

import com.crud.application.entity.PanEntity;
import com.crud.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PanRepository extends JpaRepository<PanEntity,Integer> {

    @Query(value = "select * from user_entity where user_id = (select user_id from pan_table where pan_number =:pan)",nativeQuery = true)
    Optional<UserEntity> findUserByPan(@Param("pan") String pan);
}
