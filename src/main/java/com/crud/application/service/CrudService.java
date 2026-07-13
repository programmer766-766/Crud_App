package com.crud.application.service;

import com.crud.application.dtos.UserRequestDto;
import com.crud.application.dtos.UserResponseDto;
import com.crud.application.entity.UserEntity;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
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
        if(!userRepository.existsById(userId))
            throw new NoUserFoundException("No user available in this id "+userId+"...");
       UserEntity user = userRepository.findById(userId).get();
       user.setName(userRequestDto.getName());
       user.setEmail(userRequestDto.getEmail());
       user.setCity(userRequestDto.getCity());
       userRepository.save(user);
        return "Successfully update your details Mr."+user.getName();
    }

    @Override
    public String deleteUser(int userId) {
        if(!userRepository.existsById(userId))
            throw new NoUserFoundException("No user available in this id "+userId+"...");
        String deleteInfo = userRepository.findById(userId).get().getName();
        userRepository.deleteById(userId);
        return "Mr."+deleteInfo+"your information was successfully deleted.";
    }

    public UserEntity getUserDetail(int id){
        if(!userRepository.existsById(id))
            throw new NoUserFoundException("No user available in this id "+id+"...");
        return userRepository.findById(id).get();
    }

    public boolean isExists(int id){
            return !userRepository.existsById(id);
    }
}
