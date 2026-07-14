package com.crud.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class PanDto {

    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",message = "The pan number is not valid...")
    @NotBlank
    private String panNumber;
    private LocalDateTime appliedOn;
}
