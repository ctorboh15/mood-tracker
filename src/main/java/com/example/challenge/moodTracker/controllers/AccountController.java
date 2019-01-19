package com.example.challenge.moodTracker.controllers;


import com.example.challenge.moodTracker.helpers.account.AccountHandler;
import com.example.challenge.moodTracker.helpers.account.AccountApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountHandler accountHandler;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody AccountApiObject accountSignUpRequest){
        accountHandler.doNewAccountSignUp(accountSignUpRequest);
        return new ResponseEntity("account Successfully Created", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AccountApiObject accountSignUpRequest){
        String userJwtToken = (String) accountHandler.doUserLogin(accountSignUpRequest);
        return new ResponseEntity(userJwtToken, HttpStatus.OK);
    }
}