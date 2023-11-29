package com.example.LoginPage.filestorageservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> invalidRequestExceptionHandler(InvalidRequestException ex, WebRequest request){

        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileUploadingFailedException.class)
    public ResponseEntity<String> fileUploadingFailedExceptionHandler(FileUploadingFailedException ex, WebRequest request){

        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> routeExceptionHandler(Exception ex, WebRequest request){

        return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

