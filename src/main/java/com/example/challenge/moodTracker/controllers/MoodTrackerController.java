package com.example.challenge.moodTracker.controllers;

import com.example.challenge.moodTracker.Constants;
import com.example.challenge.moodTracker.helpers.AppUserMoodEntriesApiObject;
import com.example.challenge.moodTracker.helpers.query.QueryHandler;
import com.example.challenge.moodTracker.helpers.moodentry.MoodEntryApiObject;
import com.example.challenge.moodTracker.helpers.moodentry.MoodEntryHandler;
import com.example.challenge.moodTracker.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mood")
public class MoodTrackerController {

    @Autowired
    protected MoodEntryHandler moodEntryHandler;

    @Autowired
    protected QueryHandler queryHandler;

    @PostMapping
    public ResponseEntity createNewRecord(@RequestBody MoodEntryApiObject moodEntryApiObject, HttpServletRequest request) {
        moodEntryHandler.doMoodEntry(moodEntryApiObject, (AppUser) request.getAttribute(Constants.JWT_USER_HEADER));
        return new ResponseEntity("Mood Entry Created Successfully", HttpStatus.CREATED);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AppUserMoodEntriesApiObject getAppUserMoodEntries(HttpServletRequest request){
     return queryHandler.getMoodEntriesForAppUser((AppUser) request.getAttribute(Constants.JWT_USER_HEADER));
    }
}
