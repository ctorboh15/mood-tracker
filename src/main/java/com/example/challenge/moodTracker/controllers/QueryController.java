package com.example.challenge.moodTracker.controllers;

import com.example.challenge.moodTracker.Constants;
import com.example.challenge.moodTracker.helpers.AppUserMoodEntriesApiObject;
import com.example.challenge.moodTracker.helpers.query.QueryApiObject;
import com.example.challenge.moodTracker.helpers.query.QueryHandler;
import com.example.challenge.moodTracker.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    protected QueryHandler queryHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AppUserMoodEntriesApiObject doQuery(@RequestBody QueryApiObject apiObject, HttpServletRequest request){
        return queryHandler.handleQueryObject(apiObject, (AppUser) request.getAttribute(Constants.JWT_USER_HEADER));
    }
}
