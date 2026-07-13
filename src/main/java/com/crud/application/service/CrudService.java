package com.crud.application.service;

import com.crud.application.dtos.UserRequestDto;
import com.crud.application.dtos.UserResponseDto;
import com.crud.application.entity.UserEntity;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class CrudService implements ICrudApp{

    private final UserRepository userRepository;

    /*
    Method implementation for add user,it except an UserRequestDto object from user
     */
    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        //map user data into entity fields
        UserEntity addUser=new UserEntity();
        addUser.setName(userRequestDto.getName());
        addUser.setEmail(userRequestDto.getEmail());
        addUser.setCity(userRequestDto.getCity());
        //save user entity
        userRepository.save(addUser);
        //return an Response object to the user
        return new UserResponseDto(userRequestDto.getName(),userRequestDto.getEmail(),userRequestDto.getCity());
    }
    /*
    Method implementation for get all users from UserEntity table
     */
    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new NoUserFoundException("No user available in UserEntity...");
        }
        return users;
    }

    @Override
    public String updateUser(int userId, UserRequestDto userRequestDto) {
        return "";
    }

    @Override
    public String deleteUser(int userId) {
        return "";
    }
}
