package com.example.user.webflux.controllers;

import com.example.user.webflux.dto.UserDTO;
import com.example.user.webflux.entities.UserEntity;
import com.example.user.webflux.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/save")
    Mono<UserEntity> saveUser(@RequestBody UserDTO userDTO) {
        return userServiceImpl.saveUser(userDTO);
    }
    @PutMapping("/update/{id}")
    Mono<UserEntity> updateUser(@PathVariable Long id,@RequestBody UserDTO userDTO) {
        return userServiceImpl.updateUserById(id,userDTO);
    }
    @DeleteMapping("/delete/{id}")
    Mono<ResponseEntity<String>> deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUserById(id).subscribe();
         return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).body("User deleted"));
    }
    @GetMapping()
    Flux<UserDTO> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }
    @GetMapping("/get/{id}")
    Mono<UserDTO> getUserById(@PathVariable Long id) {
        return userServiceImpl.getUserById(id);
    }
}
