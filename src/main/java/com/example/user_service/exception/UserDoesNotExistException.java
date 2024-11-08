package com.example.user_service.exception;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(){
        super("User does not exist");
    }
}
