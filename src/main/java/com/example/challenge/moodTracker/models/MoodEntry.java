package com.example.challenge.moodTracker.models;

import com.example.challenge.moodTracker.controllers.AccountController;

import javax.persistence.*;
@Entity
@Table(name="MoodEntry")
public class MoodEntry extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MoodEntryID")
    private long moodEntryId;


    @ManyToOne
    @JoinColumn(name="AppUserID", nullable=false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "MoodTypeID")
    private MoodType moodType;

    public MoodEntry() {
    }

    public MoodEntry(AppUser appUser, MoodType moodType){
        this.appUser = appUser;
        this.moodType = moodType;
    }

    public long getMoodEntryId() {
        return moodEntryId;
    }

    public void setMoodEntryId(long moodEntryId) {
        this.moodEntryId = moodEntryId;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public MoodType getMoodType() {
        return moodType;
    }

    public void setMoodType(MoodType moodType) {
        this.moodType = moodType;
    }
}
