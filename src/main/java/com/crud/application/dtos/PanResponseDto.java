package com.crud.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class PanResponseDto {
    private String name;
    private String panNumber;
    private LocalDateTime appliedOn;
}
