package com.example.challenge.moodTracker.services.interfaces;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MoodEntryService extends BaseServiceInterface<MoodEntry> {

    List<MoodEntry> findByAppUser(AppUser appUser);

    List<MoodEntry> findByMoodType(MoodType moodType);

    List<MoodEntry> findByCreatedAtBetweenAndAppUser(LocalDateTime startDate, LocalDateTime endDate, AppUser appUser);
}
