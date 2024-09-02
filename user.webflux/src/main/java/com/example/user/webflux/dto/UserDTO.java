package com.example.user.webflux.dto;

import lombok.*;


import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {
    private Long id;
   private String username,  email,  password,  firstName,  lastName;
   private List<TaskDTO> taskDTO;
   private Integer age;

    public UserDTO(Long id,String username, String email, String password, String firstName, String lastName, Integer age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
