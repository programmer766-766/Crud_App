package com.crud.application.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto{

    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String city;

    @NotNull(message = "Can't null this field")
    @Valid
    private AddressDto address;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
