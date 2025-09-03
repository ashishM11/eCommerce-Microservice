package com.manosoft.app.user_service.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manosoft.app.commons.dto.ChangePasswordRequestDTO;
import com.manosoft.app.commons.dto.UserRequestDTO;
import com.manosoft.app.commons.dto.UserSignInRequestDTO;
import com.manosoft.app.commons.response.UserResponseRecord;
import com.manosoft.app.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth(){
        return new ResponseEntity<>("API is working",HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<UserResponseRecord> getUserEmailOrMobile(@RequestParam(name = "userEmailOrMobile")String userEmailOrMobile){
        return new ResponseEntity<>(userService.getUserByEmailOrMobile(userEmailOrMobile), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userService.createUser(userRequestDTO),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<UserResponseRecord> findUserByItsId(@PathVariable(name = "userId")  Long userId){
        return new ResponseEntity<>(userService.findUserByItsId(userId),HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody UserSignInRequestDTO userSignInRequestDTO){
        return new ResponseEntity<>(userService.authenticate(userSignInRequestDTO),HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<UserResponseRecord>> findAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/forgetPassword")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Boolean> forgetPassword(@Valid @RequestParam(name = "userEmailOrMobile") String  userEmailOrMobile){
        return new ResponseEntity<>(userService.forgetPassword(userEmailOrMobile),HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        return new ResponseEntity<>(userService.changePasswordRequest(changePasswordRequestDTO),HttpStatus.OK);
    }

}

