package com.example.user.webflux.services.exceptions;

public class UserEmptyFieldException extends RuntimeException{
    public UserEmptyFieldException(String msg){
        super(msg);
    }
}
