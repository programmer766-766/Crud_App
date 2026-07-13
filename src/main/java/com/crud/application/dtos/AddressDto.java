package com.crud.application.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDto {

    @NotBlank(message = "Phone number is required as Address ID")
    private String phone;

    @NotBlank(message = "Street cannot be blank")
    private String street;

    @NotBlank(message = "Zip code cannot be blank")
    private String zip;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotBlank(message = "City cannot be blank")
    private String city;

}

