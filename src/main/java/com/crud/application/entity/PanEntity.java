package com.crud.application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAN_TABLE")
@Data
public class PanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PAN_ID")
    private Integer panId;
    @Column(name = "PAN_NUMBER")
    private String panNumber;
    @Column(name = "APPLIED_ON")
    private LocalDateTime appliedOn;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private UserEntity userId;
}
