package com.example.challenge.moodTracker.exceptions.custom;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message){
        super(message);
    }
}