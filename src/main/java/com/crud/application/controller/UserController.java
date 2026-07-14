package com.crud.application.controller;

import com.crud.application.dtos.AuthRequestDto;
import com.crud.application.dtos.PanResponseDto;
import com.crud.application.dtos.UserProfileResponseDto;
import com.crud.application.dtos.UserRequestDto;
import com.crud.application.entity.PanEntity;
import com.crud.application.entity.UserEntity;
import com.crud.application.exception.DetailsNotFoundException;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.service.CrudService;
import com.crud.application.service.JWTService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final CrudService crudService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

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
    //controller method for authenticate user
    @PostMapping("/auth")
    public ResponseEntity<Map<String,String>> authenticateUser(@Valid@RequestBody AuthRequestDto authRequest){
        Authentication authenticate = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.getUsername());
            return new ResponseEntity<>( new HashMap<String,String>(Map.of("User name", authRequest.getUsername(),"Token",token)),HttpStatus.OK);
        }

        throw new BadCredentialsException("Invalid username/password");
    }

}
