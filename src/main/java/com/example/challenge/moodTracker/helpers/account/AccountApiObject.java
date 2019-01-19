package com.example.challenge.moodTracker.helpers.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountApiObject {

    private String username;
    private String email;
    private String password;


    public AccountApiObject(){

    }

    public AccountApiObject(String username, String password, String email){
            this.email = email;
            this.username = username;
            this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
