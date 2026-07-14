package com.crud.application.controller;

import com.crud.application.dtos.AddressResponseDto;
import com.crud.application.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
@AllArgsConstructor
public class AddressController {

    private final AddressService  addressService;

    @GetMapping("/filter/city")
    public ResponseEntity<List<AddressResponseDto>> getAddressByCity(@RequestParam String city){
        return ResponseEntity.ok(addressService.getAddressByCity(city));
    }

}
