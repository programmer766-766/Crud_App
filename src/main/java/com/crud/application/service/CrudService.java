package com.crud.application.service;

import com.crud.application.dtos.*;
import com.crud.application.entity.PanEntity;
import com.crud.application.dtos.UserRequestDto;
import com.crud.application.dtos.UserResponseDto;
import com.crud.application.entity.AddressEntity;
import com.crud.application.entity.UserEntity;
import com.crud.application.exception.NoPanDataAvailableException;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.repository.PanRepository;
import com.crud.application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Service
public class CrudService implements ICrudApp,IPanData{
    private final UserRepository userRepository;
    private final PanRepository panRepository;

    /*
    Method implementation for add user,it except an UserRequestDto object from user
     */
    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {

//testing

        //map user data into entity fields
        UserEntity addUser=new UserEntity();
        addUser.setName(userRequestDto.getName());
        addUser.setEmail(userRequestDto.getEmail());
        AddressEntity addAddress=new AddressEntity();
        addAddress.setCity(userRequestDto.getCity());
        addAddress.setCountry(userRequestDto.getAddress().getCountry());
        addAddress.setStreet(userRequestDto.getAddress().getStreet());
        addAddress.setPhone(userRequestDto.getAddress().getPhone());
        addAddress.setZip(userRequestDto.getAddress().getZip());
        addAddress.setPhone(userRequestDto.getAddress().getPhone());
        addUser.setAddress(addAddress);
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
       AddressEntity addAddress=new AddressEntity();
       addAddress.setCity(userRequestDto.getCity());
       user.setAddress(addAddress);
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

    public UserEntity getUserDetail(String name){
        if(!userRepository.existsByName(name))
            throw new NoUserFoundException("No user available in this name "+name+"...");
        return userRepository.findByName(name);
    }

    public boolean isExists(int id){
            return !userRepository.existsById(id);
    }


   /*
   method implementation for add pan details to the existing user
    */
    @Override
    public PanResponseDto addPan(int userId) {
        //fetch user from db
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new NoUserFoundException("User not found with Id:" + userId));
        //store pan details into entity
        PanEntity pan=new PanEntity();
        pan.setPanNumber("husw grsd pouy");
        pan.setAppliedOn(LocalDateTime.now());
        pan.setUserId(userEntity);
        //update user entity for adding pan entity
        userEntity.setPanId(pan);
        //save
        panRepository.save(pan);
        //return proper response
        return new PanResponseDto(userEntity.getName(),"abcd xcuh oiuy",LocalDateTime.now());

    }
    /*
    Implement method for get all pan available in Pan Table
     */
    @Override
    public List<PanEntity> getAllPanData() {
        List<PanEntity> panData = panRepository.findAll();
        if(panData.isEmpty()){
            throw new NoPanDataAvailableException("No pan Available...");
        }
        return panData;
    }
    /*
     implementation method for view specific user along with pan details
     */
    @Override
    public UserProfileResponseDto showUserProfile(int userId) {
        //fetch user from db
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new NoUserFoundException("user not found with id:" + userId));
        if(userEntity.getPanId()!=null){
            //map user information into UserProfile Object
            UserProfileResponseDto userProfile=new UserProfileResponseDto();

            //PanDto object for store pan details
            PanDto panDto=new PanDto(userEntity.getPanId().getPanNumber(),userEntity.getPanId().getAppliedOn());

            userProfile.setUserId(userEntity.getUserId());
            userProfile.setName(userEntity.getName());
            userProfile.setEmail(userEntity.getEmail());
            userProfile.setPanInfo(panDto);

            //return UserProfile Dto Object
            return userProfile;
        }
        throw new NoPanDataAvailableException("No Pan Details available to the user:"+userId);
    }

}
