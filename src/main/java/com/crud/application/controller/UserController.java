package com.crud.application.controller;

import com.crud.application.dtos.PanResponseDto;
import com.crud.application.dtos.UserProfileResponseDto;
import com.crud.application.dtos.UserRequestDto;
import com.crud.application.entity.PanEntity;
import com.crud.application.entity.UserEntity;
import com.crud.application.exception.DetailsNotFoundException;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.service.CrudService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final CrudService crudService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",description = "created"),
            @ApiResponse(responseCode = "500",description = "check your connection"),
            @ApiResponse(responseCode = "409",description = "conflict already exists in database")})
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto user) throws ExecutionException, InterruptedException {
        crudService.addUser(user);
         return new ResponseEntity<>( "Adding your data...",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id,@Valid @RequestBody UserRequestDto user){

        crudService.updateUser(id,user);
        return new ResponseEntity<>( "Processing your data...",HttpStatus.OK);

    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if (!crudService.isExists(id))
            throw new NoUserFoundException("This "+id+" user doesn't exists");
    return new ResponseEntity<>(crudService.deleteUser(id),HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-id/{name}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String name){
        return new ResponseEntity<>(crudService.getUserDetail(name), HttpStatus.OK);
    }
    //Controller method for add pan card to the existing user
    @PostMapping("/{userId}/add-pan")
    public ResponseEntity<PanResponseDto> addPanIntoUser(@PathVariable int userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(crudService.addPan(userId));
    }

    //controller method for get all pan data's
    @GetMapping("/get-all/pan")
    public ResponseEntity<List<PanEntity>> getAllPans(){
        return ResponseEntity.ok(crudService.getAllPanData());
    }

    @GetMapping("/get/{userId}/info")
    public ResponseEntity<UserProfileResponseDto> showUserProfile(@PathVariable int userId){
        return ResponseEntity.ok(crudService.showUserProfile(userId));
    }
//    get pin code details
    @GetMapping("/get/pincode/{code}")
    public ResponseEntity<String> verifyPinCode(@PathVariable String code){
        return new ResponseEntity<>(crudService.verifyPinCode(code), HttpStatus.OK);
    }

    @GetMapping("/get/pan/{id}")
    public ResponseEntity<String> getUSerByPan(@PathVariable String id){
        return ResponseEntity.ok(crudService.getUserByPan(id).toString());
    }
    @PutMapping("/update/{panId}")
    public ResponseEntity<UserEntity> updateUserByPan(@PathVariable String panId,@RequestBody UserRequestDto user){
        return new ResponseEntity<>(crudService.updateUserByPan(panId,user),HttpStatus.ACCEPTED);
    }
}
