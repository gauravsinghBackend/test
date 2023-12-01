package com.example.LoginPage.OneTimePassword;

public class FailedToLoginException extends Exception{
    public FailedToLoginException(){
        super("Failed To login");
    }
}
