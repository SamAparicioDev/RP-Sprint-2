package com.example.user.webflux.mappers;

import com.example.user.webflux.dto.UserDTO;
import com.example.user.webflux.entities.UserEntity;

public class UserEntityAndUserDTO {
    public static UserEntity userDTOToUserEntity(UserDTO userDTO) {
        return new UserEntity(userDTO.getUsername(), userDTO.getEmail(),userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getAge());
    }
    public static UserDTO userEntityToUserDTO(UserEntity userEntity) {
        return new UserDTO(userEntity.getId(), userEntity.getUsername(),userEntity.getEmail(),userEntity.getPassword(),userEntity.getFirstName(),userEntity.getLastName(),userEntity.getAge());
    }
}
