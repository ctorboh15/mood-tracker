package com.example.challenge.moodTracker.dao;

import com.example.challenge.moodTracker.dao.interfaces.MoodEntryDao;
import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import com.example.challenge.moodTracker.repository.MoodEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class MoodEntryDaoImpl extends AbstractBaseDao<MoodEntry> implements MoodEntryDao {

    @Autowired
    private MoodEntryRepository moodEntryRepository;

    @Override
    public JpaRepository getRepository() {
        return moodEntryRepository;
    }

    @Override
    public List<MoodEntry> findByAppUser(AppUser appUser) {
        return moodEntryRepository.findByAppUser(appUser);
    }

    @Override
    public List<MoodEntry> findByMoodType(MoodType moodType) {
        return moodEntryRepository.findByMoodType(moodType);
    }

    @Override
    public List<MoodEntry> findByCreatedAtBetweenAndAppUser(LocalDateTime startDate, LocalDateTime endDate, AppUser appUser) {
        return moodEntryRepository.findByCreatedAtBetweenAndAppUser(startDate, endDate, appUser);
    }
}
