package com.example.challenge.moodTracker.exceptions.custom;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message){
        super(message);
    }
}
