package com.crud.application.service;

import com.crud.application.dtos.PanResponseDto;
import com.crud.application.entity.PanEntity;
import com.crud.application.entity.UserEntity;

import java.util.List;

public interface IPanData {
    PanResponseDto addPan(int userId);
    UserEntity getUserByPan(String panId);
    List<PanResponseDto> getAllPanData();
    String updatePanEmail(int userId,String email);
}
