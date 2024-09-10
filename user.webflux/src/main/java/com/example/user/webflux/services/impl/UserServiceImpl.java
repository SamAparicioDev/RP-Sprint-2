package com.example.user.webflux.services.impl;

import com.example.user.webflux.config.JwtUtils;
import com.example.user.webflux.dto.TaskDTO;
import com.example.user.webflux.dto.UserDTO;
import com.example.user.webflux.entities.UserEntity;
import com.example.user.webflux.mappers.UserEntityAndUserDTO;
import com.example.user.webflux.repositories.UserRepository;
import com.example.user.webflux.services.UserService;
import com.example.user.webflux.services.exceptions.UserEmptyFieldException;
import com.example.user.webflux.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/api/v2/task").build();

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public Mono<UserEntity> saveUser(UserDTO user) {
        validateUserBody(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(UserEntityAndUserDTO.userDTOToUserEntity(user));

    }


    @Override
    public Mono<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .flatMap((existedUser)->{
                    UserDTO userDTO = new UserDTO(existedUser.getId(), existedUser.getUsername(), existedUser.getEmail(), existedUser.getPassword(), existedUser.getFirstName(),
                            existedUser.getLastName(), existedUser.getAge());
                    return getTaskDTOByIdUser(existedUser.getId())
                            .collectList()
                            .flatMap(taskDTOS ->{
                                        userDTO.setTaskDTO(taskDTOS);
                                        return Mono.just(userDTO);
                                    });
                })
                .switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found")));
    }

    @Override
    public Flux<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .flatMap((existedUsers ->
                     getTaskDTOByIdUser(existedUsers.getId())
                            .collectList()
                            .flatMap((task)-> {
                                UserDTO userDTO = UserEntityAndUserDTO.userEntityToUserDTO(existedUsers);
                                userDTO.setTaskDTO(task);
                                return Mono.just(userDTO);
                              })));

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
            existedItem.setUsername(user.getUsername());
            existedItem.setEmail(user.getEmail());
            existedItem.setPassword(user.getPassword());
            existedItem.setFirstName(user.getFirstName());
            existedItem.setLastName(user.getLastName());
            return userRepository.save(existedItem);
        }).switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found")));
    }

    @Override
    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    public void validateUserBody(UserDTO userDTO) throws UserEmptyFieldException {
        if(userDTO.getUsername().isBlank()){
            throw new  UserEmptyFieldException("Username field is empty");
        }
        if(userDTO.getEmail().isBlank()){
            throw new  UserEmptyFieldException("Email field is empty");
        }
        if(userDTO.getPassword().isBlank()){
            throw new  UserEmptyFieldException("Password field is empty");
        }
        if(userDTO.getFirstName().isBlank()){
            throw new  UserEmptyFieldException("First name field is empty");
        }
        if(userDTO.getLastName().isBlank()){
            throw new  UserEmptyFieldException("Last name field is empty");
        }
        if(userDTO.getAge() < 0 || userDTO.getAge() == null){
            throw new  UserEmptyFieldException("Age field is empty or is less than 0");
        }
    }

    public Flux<TaskDTO> getTaskDTOByIdUser(Long id){
       return  webClient.get()
                 .uri("/get/user/" + id)
               .retrieve()
               .bodyToFlux(TaskDTO.class);
    }
}
