package com.example.challenge.moodTracker.models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Statistic")
public class Statistic extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="StatisticID")
    private long statisticId;

    @Column(name="StatisticName")
    @NonNull
    private String statisticName;

    @Column(name="StatisticValue")
    @NotNull
    private long statisticValue;

    public Statistic(){

    }

    public Statistic(String statName, long statValue){
        this.statisticName = statName;
        this.statisticValue = statValue;
    }

    public long getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(long statisticId) {
        this.statisticId = statisticId;
    }

    public String getStatisticName() {
        return statisticName;
    }

    public void setStatisticName(String statisticName) {
        this.statisticName = statisticName;
    }

    public long getStatisticValue() {
        return statisticValue;
    }

    public void setStatisticValue(long statisticValue) {
        this.statisticValue = statisticValue;
    }
}
