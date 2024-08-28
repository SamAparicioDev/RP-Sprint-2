package com.example.user.webflux.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Table("user_table")
@ToString
public class UserEntity {
    @Id
    private Long id;

    @Setter
    private String username;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private Integer age;
    public UserEntity() {}

    public UserEntity(String username, String email, String password, String firstName, String lastName,Integer age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public UserEntity(Long id,String username, String email, String password, String firstName, String lastName,Integer age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
