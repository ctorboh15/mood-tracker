package com.example.challenge.moodTracker.helpers.statistics;

import com.example.challenge.moodTracker.Constants;
import com.example.challenge.moodTracker.helpers.AppUserMoodEntriesApiObject;
import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.Statistic;
import com.example.challenge.moodTracker.services.interfaces.AppUserService;
import com.example.challenge.moodTracker.services.interfaces.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.toIntExact;

@Component
public class StatisticHandler {

    @Autowired
    protected StatisticService statisticService;

    @Autowired
    protected AppUserService appUserService;

    public void incrementStatistic(String statisticName){
        Statistic statistic = getStatistic(statisticName);
        if (statistic != null){
            long statisticValue = statistic.getStatisticValue();
            statistic.setStatisticValue(++statisticValue);
            statisticService.update(statistic);
        }
    }

    public Statistic getStatistic(String statisticName){
        Optional<Statistic> statistic = Optional.ofNullable(statisticService.findByStatisticName(statisticName));
        if(statistic.isPresent()){
            return statistic.get();
        }
        return null;
    }

    public void createStatistic(String statisticName){
        Optional<Statistic> statistic = Optional.ofNullable(statisticService.findByStatisticName(statisticName));

        if(!statistic.isPresent()){
            Statistic newStatistic = new Statistic(Constants.STATISTIC_ACTIVE_APP_USERS_COUNT, 0);
            statisticService.create(newStatistic);
        }
    }

    public void implementStreakStatistic(AppUserMoodEntriesApiObject appUserMoodEntriesApiObject, AppUser appUser){
        Statistic activeUserStat = getStatistic(Constants.STATISTIC_ACTIVE_APP_USERS_COUNT);
        List<AppUser> list = appUserService.findAllByOrderByMoodEntryStreakDesc();

        AppUser[] appUsersArray = list.toArray(new AppUser[list.size()]);
        int streakIndex = binarySearchForUserStreak(appUsersArray, appUserMoodEntriesApiObject.getMoodEntryStreak());

        if(streakIndex > -1){
            if(appUsersArray[streakIndex].getUsername().equals(appUser.getUsername())){
                calculatePercentageBasedOnIndex(activeUserStat.getStatisticValue(), streakIndex, appUserMoodEntriesApiObject);
                return;
            }

            int leftPointer  = streakIndex -1;
            int rightPointer = streakIndex +1;

            while(true){
                AppUser userAtLeftPointer = appUsersArray[leftPointer];
                AppUser userAtRightPointer = appUsersArray[rightPointer];



               if(userAtLeftPointer.getMoodEntryStreak() == appUser.getMoodEntryStreak()){
                   if(userAtLeftPointer.getUsername().equals(appUser.getUsername())){
                       calculatePercentageBasedOnIndex(activeUserStat.getStatisticValue(), leftPointer, appUserMoodEntriesApiObject);
                       return;
                   }
                   else {
                       leftPointer -= 1;
                   }
               }
                if(userAtRightPointer.getMoodEntryStreak() == appUser.getMoodEntryStreak()){
                    if(userAtLeftPointer.getUsername().equals(appUser.getUsername())){
                        calculatePercentageBasedOnIndex(activeUserStat.getStatisticValue(), rightPointer, appUserMoodEntriesApiObject);
                        return;
                    }
                    else {
                        rightPointer += 1;
                    }
                }

                if(userAtLeftPointer.getMoodEntryStreak() != appUser.getMoodEntryStreak() && userAtRightPointer.getMoodEntryStreak() != appUser.getMoodEntryStreak()){
                    return;
                }
            }
        }
    }

    private void calculatePercentageBasedOnIndex(Long totalActiveUsers, int userStreakIndex, AppUserMoodEntriesApiObject appUserMoodEntriesApiObject){
        long percentage = ((userStreakIndex+1) * 100 / toIntExact(totalActiveUsers));
        appUserMoodEntriesApiObject.setRank(percentage + " %");
    }

    private int binarySearchForUserStreak(AppUser[] arr, int key) {
        int lowerBound = 0;
        int upperBound = arr.length - 1;

        while (true) {
            int midIndex = (lowerBound + upperBound) / 2;

            if (arr[midIndex].getMoodEntryStreak() == key) {
                return midIndex;
            } else if (lowerBound > upperBound) {
                return -1;
            } else {
                // Mid index less than key so its in bottom half since list is in desc order
                if (arr[midIndex].getMoodEntryStreak() < key) {
                    upperBound = midIndex - 1;

                } else {
                    lowerBound = midIndex + 1;
                }
            }
        }
    }

}
