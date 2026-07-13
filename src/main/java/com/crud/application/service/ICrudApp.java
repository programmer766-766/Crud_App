package com.crud.application.service;

import com.crud.application.dtos.UserRequestDto;
import com.crud.application.dtos.UserResponseDto;
import com.crud.application.entity.UserEntity;

import java.util.List;

public interface ICrudApp {
    UserResponseDto addUser(UserRequestDto userRequestDto);
    List<UserEntity> getAllUsers();
    String updateUser(int userId, UserRequestDto userRequestDto);
    String deleteUser(int userId);
}
