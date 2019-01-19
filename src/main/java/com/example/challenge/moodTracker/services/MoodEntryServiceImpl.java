package com.example.challenge.moodTracker.services;

import com.example.challenge.moodTracker.dao.interfaces.BaseDao;
import com.example.challenge.moodTracker.dao.interfaces.MoodEntryDao;
import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import com.example.challenge.moodTracker.services.interfaces.MoodEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class MoodEntryServiceImpl extends AbstractBaseService<MoodEntry> implements MoodEntryService {

    @Autowired
    private MoodEntryDao moodEntryDao;

    @Override
    protected BaseDao<MoodEntry> getDAO() {
        return moodEntryDao;
    }

    @Override
    public List<MoodEntry> findByAppUser(AppUser appUser) {
        return moodEntryDao.findByAppUser(appUser);
    }

    @Override
    public List<MoodEntry> findByMoodType(MoodType moodType) {
        return moodEntryDao.findByMoodType(moodType);
    }

    @Override
    public List<MoodEntry> findByCreatedAtBetweenAndAppUser(LocalDateTime startDate, LocalDateTime endDate, AppUser appUser) {
        return moodEntryDao.findByCreatedAtBetweenAndAppUser(startDate, endDate, appUser);
    }
}
