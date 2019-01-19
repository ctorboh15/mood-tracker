package com.example.challenge.moodTracker.helpers.moodentry;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;

public class MoodEntryApiObject {

    private String desc;
    private String userName;
    private LocalDateTime created;

    public MoodEntryApiObject() {

    }

    public MoodEntryApiObject(MoodEntry moodEntry){
        this.userName =  moodEntry.getAppUser().getUsername();
        this.desc = moodEntry.getMoodType().getDescription();
        this.created = moodEntry.getCreatedAt();
    }

    public MoodEntryApiObject(AppUser user, MoodEntry moodEntry){
        this.desc = moodEntry.getMoodType().getDescription();
        this.userName = user.getUsername();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonIgnore
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonIgnore
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
