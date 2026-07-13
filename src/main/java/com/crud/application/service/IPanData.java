package com.crud.application.service;

import com.crud.application.dtos.PanResponseDto;
import com.crud.application.entity.PanEntity;

import java.util.List;

public interface IPanData {
    PanResponseDto addPan(int userId);
    List<PanEntity> getAllPanData();
}
