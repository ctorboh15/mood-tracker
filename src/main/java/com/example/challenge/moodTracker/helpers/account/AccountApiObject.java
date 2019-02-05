package com.example.challenge.moodTracker.helpers.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AccountApiObject {

    private String username;
    private String email;
    private String password;
    private int moodEntryStreak;
    private LocalDateTime lastMoodInputDate;

    private AccountApiObject(){

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

    @JsonIgnore
    public int getMoodEntryStreak() {
        return moodEntryStreak;
    }

    public void setMoodEntryStreak(int moodEntryStreak) {
        this.moodEntryStreak = moodEntryStreak;
    }
    @JsonIgnore
    public LocalDateTime getLastMoodInputDate() {
        return lastMoodInputDate;
    }

    public void setLastMoodInputDate(LocalDateTime lastMoodInputDate) {
        this.lastMoodInputDate = lastMoodInputDate;
    }

    private AccountApiObject(Builder builder){
        this.username = builder.username;
        this.email = builder.email;
        this.moodEntryStreak = builder.moodEntryStreak;
        this.lastMoodInputDate = builder.lastMoodInputDate;
    }

    public static class Builder {
        private String username;
        private String email;
        private int moodEntryStreak = 0;
        private LocalDateTime lastMoodInputDate = null;

        public Builder(String username, String email){
            this.username = username;
            this.email = email;
        }

        public Builder moodStreakEntry(int moodEntryStreak){
            this.moodEntryStreak = moodEntryStreak;
            return this;
        }

        public Builder lastMoodInputDate(LocalDateTime lastMoodInputDate){
            this.lastMoodInputDate = lastMoodInputDate;
            return this;
        }


        public AccountApiObject build(){
            return new AccountApiObject(this);
        }
    }
}
