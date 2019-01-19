package com.example.challenge.moodTracker.services.interfaces;

import com.example.challenge.moodTracker.models.AppUser;

import java.util.List;

public interface AppUserService extends BaseServiceInterface<AppUser> {

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    AppUser findByEmailAndUsername(String email, String username);

    AppUser findByEmailOrUsername(String email, String username);

    List<AppUser> findAllByOrderByMoodEntryStreakDesc();
}


