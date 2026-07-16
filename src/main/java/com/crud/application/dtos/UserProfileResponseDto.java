package com.crud.application.dtos;

import lombok.Data;

@Data
public class UserProfileResponseDto {
    private Integer userId;
    private String name;
    private String email;
    private String city;
    private PanDto panInfo;
}
