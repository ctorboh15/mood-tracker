package com.example.challenge.moodTracker.services.interfaces;

import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodTypeService extends BaseServiceInterface<MoodType> {

    MoodType findMoodByDescription(String desc);

    List<MoodType> findAllActive();
}
