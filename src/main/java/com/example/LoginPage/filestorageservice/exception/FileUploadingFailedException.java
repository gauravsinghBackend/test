package com.zevo360.filestorageservice.exception;

public class FileUploadingFailedException extends RuntimeException{
    public FileUploadingFailedException(String message){
        super(message);
    }
}
