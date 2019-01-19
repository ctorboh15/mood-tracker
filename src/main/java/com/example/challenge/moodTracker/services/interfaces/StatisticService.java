package com.example.challenge.moodTracker.services.interfaces;

import com.example.challenge.moodTracker.models.Statistic;

public interface StatisticService extends  BaseServiceInterface<Statistic> {

    Statistic findByStatisticName(String statisticName);
}
