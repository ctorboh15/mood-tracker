package com.example.challenge.moodTracker.repository;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {

    List<MoodEntry> findByAppUser(AppUser appUser);

    List<MoodEntry> findByMoodType(MoodType moodType);

    List<MoodEntry> findByCreatedAtBetweenAndAppUser(LocalDateTime startDate, LocalDateTime endDate, AppUser appUser);

}
