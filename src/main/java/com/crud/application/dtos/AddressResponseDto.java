package com.crud.application.dtos;

import lombok.Data;

@Data
public class AddressResponseDto {
    private String city;
    private String street;
    private String zip;
    private String country;
    private String state;
}
