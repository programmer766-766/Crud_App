package com.crud.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanResponseDto {
    private Integer panId;
    private String name;
    private String panNumber;
    private LocalDateTime appliedOn;
    private String email;

    public PanResponseDto(String name, String panNumber, LocalDateTime appliedOn, String email) {
        this.name = name;
        this.panNumber = panNumber;
        this.appliedOn = appliedOn;
        this.email = email;
    }
}
