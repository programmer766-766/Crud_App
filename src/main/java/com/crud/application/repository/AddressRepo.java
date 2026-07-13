package com.crud.application.repository;

import com.crud.application.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<AddressEntity, String> {
}
