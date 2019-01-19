package com.example.challenge.moodTracker.dao.interfaces;

import com.example.challenge.moodTracker.models.AppUser;

import java.util.List;

public interface AppUserDao extends BaseDao<AppUser> {

    AppUser findByUserName(String userName);

    AppUser findByEmail(String email);

    AppUser findByEmailAndUsername(String email, String username);

    AppUser findByEmailOrUsername(String email, String username);

    List<AppUser> findAllByOrderByMoodEntryStreakDesc();
}
