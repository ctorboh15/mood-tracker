package com.example.challenge.moodTracker.dao.interfaces;

import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;

import java.util.List;

public interface MoodTypeDao extends BaseDao<MoodType> {

    MoodType findMoodTypeByDescription(String desc);

    List<MoodType> findAllActive();
}
