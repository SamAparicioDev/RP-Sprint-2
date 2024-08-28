package com.example.user.webflux.services.impl;

import com.example.user.webflux.dto.UserDTO;
import com.example.user.webflux.entities.UserEntity;
import com.example.user.webflux.mappers.UserEntityAndUserDTO;
import com.example.user.webflux.repositories.UserRepository;
import com.example.user.webflux.services.UserService;
import com.example.user.webflux.services.exceptions.UserEmptyFieldException;
import com.example.user.webflux.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserEntityAndUserDTO userEntityAndUserDTO = new UserEntityAndUserDTO();

    @Override
    public Mono<UserEntity> save(UserDTO user) {
        validateUserBody(user);
        return (userRepository.save(userEntityAndUserDTO.userDTOToUserEntity(user)));
    }

    @Override
    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id).flatMap(Mono::just).switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found")));
    }

    @Override
    public Flux<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Void> deleteUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)))
                .flatMap(existedUser -> userRepository.deleteById(existedUser.getId()));
    }


    @Override
    public Mono<UserEntity> updateUserById(Long id, UserDTO user) {
        return userRepository.findById(id).flatMap((existedItem)->{
            validateUserBody(user);
            existedItem.setUsername(user.username());
            existedItem.setEmail(user.email());
            existedItem.setPassword(user.password());
            existedItem.setFirstName(user.firstName());
            existedItem.setLastName(user.lastName());
            return userRepository.save(existedItem);
        }).switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found")));
    }

    public void validateUserBody(UserDTO userDTO) throws UserEmptyFieldException {
        if(userDTO.username().isBlank()){
            throw new  UserEmptyFieldException("Username field is empty");
        }
        if(userDTO.email().isBlank()){
            throw new  UserEmptyFieldException("Email field is empty");
        }
        if(userDTO.password().isBlank()){
            throw new  UserEmptyFieldException("Password field is empty");
        }
        if(userDTO.firstName().isBlank()){
            throw new  UserEmptyFieldException("First name field is empty");
        }
        if(userDTO.lastName().isBlank()){
            throw new  UserEmptyFieldException("Last name field is empty");
        }
        if(userDTO.age() < 0 || userDTO.age() == null){
            throw new  UserEmptyFieldException("Age field is empty or is less than 0");
        }
    }
}
