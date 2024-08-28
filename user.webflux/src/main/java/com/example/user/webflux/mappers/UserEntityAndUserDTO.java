package com.example.user.webflux.mappers;

import com.example.user.webflux.dto.UserDTO;
import com.example.user.webflux.entities.UserEntity;

public class UserEntityAndUserDTO {
    public UserEntity userDTOToUserEntity(UserDTO userDTO) {
        return new UserEntity(userDTO.username(), userDTO.email(),userDTO.password(), userDTO.firstName(), userDTO.lastName(), userDTO.age());
    }
}
