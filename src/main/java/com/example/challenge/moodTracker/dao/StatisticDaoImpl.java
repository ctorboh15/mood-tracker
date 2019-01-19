package com.example.challenge.moodTracker.dao;

import com.example.challenge.moodTracker.dao.interfaces.StatisticDao;
import com.example.challenge.moodTracker.models.Statistic;
import com.example.challenge.moodTracker.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class StatisticDaoImpl extends AbstractBaseDao<Statistic> implements StatisticDao {

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public JpaRepository getRepository() {
        return statisticRepository;
    }

    @Override
    public Statistic findByStatisticName(String statisticName) {
        return statisticRepository.findByStatisticName(statisticName);
    }
}