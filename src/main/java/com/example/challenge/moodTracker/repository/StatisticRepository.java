package com.example.challenge.moodTracker.repository;

import com.example.challenge.moodTracker.models.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    Statistic findByStatisticName(String statisticName);
}
