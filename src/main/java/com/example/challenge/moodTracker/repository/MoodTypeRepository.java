package com.example.challenge.moodTracker.repository;

import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoodTypeRepository extends JpaRepository<MoodType, Long> {

    MoodType findMoodTypeByDescription(String desc);


    @Query("SELECT mt FROM MoodType mt WHERE mt.active = 'yes'")
    List<MoodType> findAllActive();
}
