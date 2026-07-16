package com.crud.application.service;

import com.crud.application.dtos.UserProfileResponseDto;
import com.crud.application.dtos.UserRequestDto;
import com.crud.application.dtos.UserResponseDto;
import com.crud.application.entity.UserEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICrudApp {
    CompletableFuture<UserResponseDto> addUser(UserRequestDto userRequestDto);
    List<UserEntity> getAllUsers();
    CompletableFuture<String> updateUser(int userId, UserRequestDto userRequestDto);
    String deleteUser(int userId);
    UserProfileResponseDto showUserProfile(int userId);
}
