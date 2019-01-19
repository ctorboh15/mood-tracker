package com.example.challenge.moodTracker.dao.interfaces;

import com.example.challenge.moodTracker.models.Statistic;

public interface StatisticDao extends BaseDao<Statistic> {

    Statistic findByStatisticName(String statisticName);

}
