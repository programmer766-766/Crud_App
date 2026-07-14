package com.crud.application.service;

import com.crud.application.dtos.AddressResponseDto;
import com.crud.application.entity.AddressEntity;
import com.crud.application.repository.AddressRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepo addressRepo;

   /*
   method for get address by city
    */
    public List<AddressResponseDto> getAddressByCity(String city){
        List<AddressEntity> address = addressRepo.getAddressByCity(city);

        return address.stream().map(add->{
            AddressResponseDto addressResponseDto=new AddressResponseDto();
            addressResponseDto.setCity(add.getCity());
            addressResponseDto.setState(add.getState());
            addressResponseDto.setZip(add.getZip());
            addressResponseDto.setStreet(add.getStreet());
            addressResponseDto.setCountry(add.getCountry());

            return addressResponseDto;
        }).toList();
    }
}
