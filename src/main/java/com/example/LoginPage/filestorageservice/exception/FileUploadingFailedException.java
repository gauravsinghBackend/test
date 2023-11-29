package com.example.LoginPage.filestorageservice.exception;

public class FileUploadingFailedException extends RuntimeException{
    public FileUploadingFailedException(String message){
        super(message);
    }
}
