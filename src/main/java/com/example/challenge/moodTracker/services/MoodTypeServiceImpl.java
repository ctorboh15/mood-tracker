package com.example.challenge.moodTracker.services;

import com.example.challenge.moodTracker.dao.interfaces.BaseDao;
import com.example.challenge.moodTracker.dao.interfaces.MoodTypeDao;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import com.example.challenge.moodTracker.services.interfaces.MoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodTypeServiceImpl extends AbstractBaseService<MoodType> implements MoodTypeService {

    @Autowired
    private MoodTypeDao moodTypeDao;

    @Override
    protected BaseDao<MoodType> getDAO() {
        return moodTypeDao;
    }

    @Override
    public MoodType findMoodByDescription(String desc) {
        return moodTypeDao.findMoodTypeByDescription(desc);
    }

    @Override
    public List<MoodType> findAllActive() {
        return moodTypeDao.findAllActive();
    }
}
