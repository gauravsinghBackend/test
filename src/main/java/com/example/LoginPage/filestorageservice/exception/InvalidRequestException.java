package com.example.LoginPage.filestorageservice.exception;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String message){
        super(message);
    }
}
