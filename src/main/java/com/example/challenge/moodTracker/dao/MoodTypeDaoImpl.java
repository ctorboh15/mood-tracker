package com.example.challenge.moodTracker.dao;

import com.example.challenge.moodTracker.dao.interfaces.MoodTypeDao;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import com.example.challenge.moodTracker.repository.MoodTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoodTypeDaoImpl extends AbstractBaseDao<MoodType> implements MoodTypeDao {

    @Autowired
    private MoodTypeRepository moodTypeRepository;
    @Override
    public JpaRepository getRepository() {
        return moodTypeRepository;
    }

    @Override
    public MoodType findMoodTypeByDescription(String desc) {
        return moodTypeRepository.findMoodTypeByDescription(desc);
    }

    @Override
    public List<MoodType> findAllActive() {
        return moodTypeRepository.findAllActive();
    }
}
