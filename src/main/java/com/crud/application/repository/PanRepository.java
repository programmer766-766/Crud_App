package com.crud.application.repository;

import com.crud.application.entity.PanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanRepository extends JpaRepository<PanEntity,Integer> {
}
