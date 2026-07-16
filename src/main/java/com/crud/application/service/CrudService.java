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
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class CrudService implements ICrudApp,IPanData,PinCode{

    private final UserRepository userRepository;
    private final PanRepository panRepository;
    private final AddressRepo addressRepo;
    private Bucket bucket;

    /*
    Method implementation for add user,it except an UserRequestDto object from user
     */
//    @Async("Email")
    @Override
    @Async("Email")
    public CompletableFuture<UserResponseDto> addUser(UserRequestDto userRequestDto) {
        //map user data into entity fields
        UserEntity addUser=new UserEntity();
        PanEntity panEntity=new PanEntity();
        AddressEntity addAddress=new AddressEntity();
        if (!bucket.tryConsume(1))
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"your limit exceeded");
        try{
            System.out.println("Adding to database...");
            Thread.sleep(3000);
            addUser.setName(userRequestDto.getName());
            addUser.setEmail(userRequestDto.getEmail());
            addUser.setPanId(panEntity);
            addUser.setAddress(addAddress);

            panEntity.setPanNumber(autoPanNumber());
            panEntity.setUserId(addUser);

            addAddress.setCity(userRequestDto.getCity());
            addAddress.setCountry(userRequestDto.getAddress().getCountry());
            addAddress.setStreet(userRequestDto.getAddress().getStreet());
            addAddress.setPhone(userRequestDto.getAddress().getPhone());
            addAddress.setZip(userRequestDto.getAddress().getZip());
            userRepository.save(addUser);
            return CompletableFuture.completedFuture(new UserResponseDto(userRequestDto.getName(),userRequestDto.getEmail(),userRequestDto.getCity()));



        }
        catch (InterruptedException ex)
        {
            System.out.println(ex.getMessage());
        return CompletableFuture.failedFuture(ex);
        }

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

    @CachePut(cacheNames = "pan",key = "#userId")
    @Override
    @Async("Email")
    public CompletableFuture<String> updateUser(int userId, UserRequestDto userRequestDto) {
        try {
            System.out.println("Saving...");
            Thread.sleep(3000);
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoUserFoundException("User not found in this id : " + userId));
            user.setName(userRequestDto.getName());
            user.setEmail(userRequestDto.getEmail());
            AddressEntity addAddress = new AddressEntity();
            addAddress.setCity(userRequestDto.getCity());
            user.setAddress(addAddress);
            userRepository.save(user);
            return CompletableFuture.completedFuture("Successfully update your details Mr." + user.getName());
        } catch (InterruptedException ex) {
            return CompletableFuture.completedFuture(ex.getMessage());
        }
    }

    @Override
    public String deleteUser(int userId) {

        String deleteInfo = userRepository.findById(userId)
                .orElseThrow(
                ()->new NoUserFoundException("No user available in this id "+userId+"...")).getName();
        userRepository.deleteById(userId);
        return "Mr."+deleteInfo+"your information was successfully deleted.";
    }
    @Cacheable(value = "pan",key = "#result.name")
    public UserEntity getUserDetail(String name){
        return userRepository.findByName(name)
                .orElseThrow(
                ()->new NoUserFoundException("No user available in this name "+name+"..."));
    }

    public boolean isExists(int id){
            return userRepository.existsById(id);
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
        //update user entity for adding pan entity
        userEntity.setPanId(pan);
        //save
        panRepository.save(pan);
        //return proper response
        return new PanResponseDto(userEntity.getName(),autoPanNumber(),LocalDateTime.now());

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
    public List<PanEntity> getAllPanData() {
        List<PanEntity> panData = panRepository.findAll();
        if(panData.isEmpty()){
            throw new NoPanDataAvailableException("No pan Available...");
        }
        return panData;
    }
// get user details by pan
    @Cacheable(value = "pan",key = "#panId")
    @Override
    public UserEntity getUserByPan(String panId) {
        System.err.println("getUserByPan");
        return panRepository.findUserByPan(panId)
                .orElseThrow(()->new NoPanDataAvailableException("No pan Available..."));
    }
    @CachePut(value = "pan",key = "#panId")
    public UserEntity updateUserByPan(String panId, UserRequestDto userRequestDto) {
        UserEntity userEntity = panRepository.findUserByPan(panId).get();
        userEntity.setName(userRequestDto.getName());
        return userRepository.save(userEntity);
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

    @Cacheable(value = "pan",key = "#pinCode")
    @Override
    public String verifyPinCode(String pinCode) {
        if(!addressRepo.isExists(pinCode))
            throw new PinCodeNotFoundException("PinCode Not Found...");
        AddressEntity address = addressRepo.findAreaByZipCode(pinCode).get();
        return address.getCity()+address.getCountry()+address.getState();
    }
}
