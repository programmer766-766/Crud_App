package com.crud.application.repository;

import com.crud.application.dtos.AddressResponseDto;
import com.crud.application.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<AddressEntity, String> {

    @Query(value = "select * from Address_Details where pin =:zip",nativeQuery = true)
    Optional<AddressEntity> findAreaByZipCode(@Param("zip") String zipCode);

    @Query(value = "select EXISTS(select true from Address_Details where pin =:code ) ",nativeQuery = true)
    boolean isExists(@Param("code") String zipcode);

    //JPQL query for get address by city
    @Query(value = "SELECT a FROM AddressEntity a WHERE a.city=:city")
    List<AddressEntity> getAddressByCity(@Param("city")String city);
}
