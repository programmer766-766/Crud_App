package com.crud.application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// Inverse table
@Entity
@Data
@Table(name = "Address_Details")
public class AddressEntity {

    @Column(name = "CITY")
    private String city;
    @Column(name = "STREET")
    private String street;
    @Column(name = "PIN")
    private String zip;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "STATE")
    private String state;
    @Id
    @Column(name = "PHONE")
    private String phone;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<UserEntity> user = new ArrayList<>();
}
