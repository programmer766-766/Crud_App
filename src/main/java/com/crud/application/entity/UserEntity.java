package com.crud.application.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "USER_ENTITY")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CITY")
    private String city;
}
