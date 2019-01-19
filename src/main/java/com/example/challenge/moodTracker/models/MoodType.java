package com.example.challenge.moodTracker.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity
@Table(name="MoodType")
public class MoodType extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MoodTypeID")
    private long moodTypeId;

    @Column
    @NotNull
    private String description;


    @Type(type = "yes_no")
    private boolean active = true;

    public String getDescription() {
        return description;
    }

    public MoodType() {

    }

    public MoodType(String description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getMoodTypeId() {
        return moodTypeId;
    }

    public void setMoodTypeId(long moodTypeId) {
        this.moodTypeId = moodTypeId;
    }
}
