package com.example.user.webflux.services;

import com.example.user.webflux.dto.UserDTO;
import com.example.user.webflux.entities.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserEntity> save(UserDTO user);
    Mono<UserDTO> getUserById(Long id);
    Flux<UserDTO> getAllUsers();
    Mono<Void> deleteUserById(Long id);
    Mono<UserEntity> updateUserById(Long id, UserDTO user);

}
