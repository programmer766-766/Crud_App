package com.crud.application.service;

import com.crud.application.dtos.*;
import com.crud.application.entity.PanEntity;
import com.crud.application.dtos.UserRequestDto;
import com.crud.application.dtos.UserResponseDto;
import com.crud.application.entity.AddressEntity;
import com.crud.application.entity.UserEntity;
import com.crud.application.exception.NoPanDataAvailableException;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.exception.PinCodeNotFoundException;
import com.crud.application.repository.AddressRepo;
import com.crud.application.repository.PanRepository;
import com.crud.application.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CrudService implements ICrudApp,IPanData,PinCode{

    private final UserRepository userRepository;
    private final PanRepository panRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepo addressRepo;

    /*
    Method implementation for add user,it except an UserRequestDto object from user
     */
    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {

//testing
panRepository.findAll();
        //map user data into entity fields

        UserEntity addUser=new UserEntity();
        addUser.setName(userRequestDto.getName());
        addUser.setEmail(userRequestDto.getEmail());
        PanEntity panEntity=new PanEntity();
        panEntity.setPanNumber(autoPanNumber());
        panEntity.setUserId(addUser);
        addUser.setPanId(panEntity);
        addUser.setUsername(userRequestDto.getUsername());
        addUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        addUser.setRole("ROLE_USER");

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



    public void demo(){
        System.out.println("bye bye");
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
        pan.setHolderName(userEntity.getName());
        pan.setEmail(userEntity.getEmail());
        //update user entity for adding pan entity
        userEntity.setPanId(pan);
        //save
        panRepository.save(pan);
        //return proper response
        return new PanResponseDto(userEntity.getName(),autoPanNumber(),LocalDateTime.now(),userEntity.getEmail());

    }

    protected String autoPanNumber(){
        String five = RandomStringUtils.randomAlphabetic(5).toUpperCase();
        String four = RandomStringUtils.randomNumeric(4);
        String one = RandomStringUtils.randomAlphabetic(1).toUpperCase();
        return  five+four+one;
    }

    /*
    Implement method for get all pan available in Pan Table
     */
    @Override
    public List<PanResponseDto> getAllPanData() {
        List<PanEntity> panData = panRepository.findAll();
        if(panData.isEmpty()){
            throw new NoPanDataAvailableException("No pan Available...");
        }
        //Map the Pan Entity details to the Response Object
       return panData.stream().map(pan->{
           PanResponseDto panResponseDto=new PanResponseDto();
           panResponseDto.setPanNumber(pan.getPanNumber());
           panResponseDto.setName(pan.getHolderName());
           panResponseDto.setEmail(pan.getEmail());
           panResponseDto.setAppliedOn(pan.getAppliedOn());
           panResponseDto.setPanId(pan.getPanId());

           return panResponseDto;
       }).toList();
    }
// get user details by pan
    @Override
    public UserEntity getUserByPan(String panId) {
        return panRepository.findUserByPan(panId)
                .orElseThrow(()->new NoPanDataAvailableException("No pan Available..."));
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

    @Override
    public String verifyPinCode(String pinCode) {
        if(!addressRepo.isExists(pinCode))
            throw new PinCodeNotFoundException("PinCode Not Found...");
        AddressEntity address = addressRepo.findAreaByZipCode(pinCode).get();
        return address.getCity()+address.getCountry()+address.getState();
    }

    @Override
    public String updatePanEmail(int userId, String email) {
        int rowAffected = userRepository.updatePanEmail(userId, email);
        if(rowAffected>0){
            return "Update completed!!";
        }
        throw new RuntimeException("Updation Failed");
    }
}
