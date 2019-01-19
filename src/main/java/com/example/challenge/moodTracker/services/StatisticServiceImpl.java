package com.example.challenge.moodTracker.services;

import com.example.challenge.moodTracker.dao.interfaces.BaseDao;
import com.example.challenge.moodTracker.dao.interfaces.StatisticDao;
import com.example.challenge.moodTracker.models.Statistic;
import com.example.challenge.moodTracker.services.interfaces.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl extends AbstractBaseService<Statistic> implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    @Override
    protected BaseDao<Statistic> getDAO() {
        return statisticDao;
    }

    @Override
    public Statistic findByStatisticName(String statisticName) {
        return statisticDao.findByStatisticName(statisticName);
    }
}
