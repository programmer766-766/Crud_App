package com.crud.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class PanDto {
    private String panNumber;
    private LocalDateTime appliedOn;
}
