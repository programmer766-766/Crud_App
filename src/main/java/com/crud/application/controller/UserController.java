package com.crud.application.controller;

import com.crud.application.dtos.UserRequestDto;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto user){
        if(user==null)
            throw new DetailsNotFoundException("User cannot be null");
       return new ResponseEntity<>( "Mr."+crudService.addUser(user)
               .getName()+"your details was successfully added",HttpStatus.CREATED);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id,@Valid @RequestBody UserRequestDto user){
        if (crudService.isExists(id))
            throw new DetailsNotFoundException("User cannot be null");
        return new ResponseEntity<>( crudService.updateUser(id,user),HttpStatus.OK);

    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if (crudService.isExists(id))
            throw new NoUserFoundException("This "+id+" user doesn't exists");
    return new ResponseEntity<>(crudService.deleteUser(id),HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable int id){
        return new ResponseEntity<>(crudService.getUserDetail(id), HttpStatus.OK);
    }
}
