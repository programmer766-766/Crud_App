package com.crud.application.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAN_TABLE")
@Data
public class PanEntity {
    @Id
    @Column(name = "PAN_NUMBER")
    private String panNumber;
    @Column(name = "APPLIED_ON")
    private LocalDateTime appliedOn;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private UserEntity userId;

    public String toString(){
        return  userId + " " + appliedOn;
    }
}
