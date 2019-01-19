package com.example.challenge.moodTracker.helpers;

import com.example.challenge.moodTracker.helpers.moodentry.MoodEntryApiObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppUserMoodEntriesApiObject {

    DateFormat df;
    private String rangeStartDate;
    private String rangeEndDate;
    private int moodEntryStreak;
    private List<MoodEntryApiObject> moodEntries;
    private String rank;


    public AppUserMoodEntriesApiObject() {
        df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    }

    public AppUserMoodEntriesApiObject(List<MoodEntryApiObject> moodEntries){
        this.moodEntries = moodEntries;
    }

    public AppUserMoodEntriesApiObject(List<MoodEntryApiObject> moodEntries, int moodEntryStreak){
        this(moodEntries);
        this.moodEntryStreak = moodEntryStreak;
    }

    public AppUserMoodEntriesApiObject(Date rangeStartDate, Date rangeEndDate){
        this();
        this.rangeStartDate = df.format(rangeStartDate);
        this.rangeEndDate = df.format(rangeEndDate);
    }

    public String getRangeStartDate() {
        return rangeStartDate;
    }

    public void setRangeStartDate(String rangeStartDate) {
        this.rangeStartDate = rangeStartDate;
    }

    public String getRangeEndDate() {
        return rangeEndDate;
    }

    public void setRangeEndDate(String rangeEndDate) {
        this.rangeEndDate = rangeEndDate;
    }

    public List<MoodEntryApiObject> getMoodEntries() {
        return moodEntries;
    }

    public void setMoodEntries(List<MoodEntryApiObject> moodEntries) {
        this.moodEntries = moodEntries;
    }

    public int getMoodEntryStreak() {
        return moodEntryStreak;
    }

    public void setMoodEntryStreak(int moodEntryStreak) {
        this.moodEntryStreak = moodEntryStreak;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
