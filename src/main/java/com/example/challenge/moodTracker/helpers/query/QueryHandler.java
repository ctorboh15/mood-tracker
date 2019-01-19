package com.example.challenge.moodTracker.helpers.query;

import com.example.challenge.moodTracker.Constants;
import com.example.challenge.moodTracker.exceptions.custom.BadRequestException;
import com.example.challenge.moodTracker.helpers.AppUserMoodEntriesApiObject;
import com.example.challenge.moodTracker.helpers.moodentry.MoodEntryApiObject;
import com.example.challenge.moodTracker.helpers.statistics.StatisticHandler;
import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.services.interfaces.MoodEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueryHandler {

    public HashSet<String> queryTypes;

    @Autowired
    protected MoodEntryService moodEntryService;

    @Autowired
    protected StatisticHandler statisticHandler;

    public QueryHandler(){
        loadQueryTpyeHashSet();
    }

    public AppUserMoodEntriesApiObject getMoodEntriesForAppUser(AppUser appUser){
        List<MoodEntryApiObject> moodEntryApiObjects =  moodEntryService.findByAppUser(appUser).stream().map(moodEntry -> new MoodEntryApiObject(moodEntry)).collect(Collectors.toList());
         AppUserMoodEntriesApiObject appUserMoodEntriesApiObject = new AppUserMoodEntriesApiObject(moodEntryApiObjects, appUser.getMoodEntryStreak());
         statisticHandler.implementStreakStatistic(appUserMoodEntriesApiObject, appUser);
         return appUserMoodEntriesApiObject;
    }

    public AppUserMoodEntriesApiObject handleQueryObject(QueryApiObject queryApiObject, AppUser appUser){
        if(!queryTypes.contains(queryApiObject.getQueryType())){
            throw new BadRequestException("The query type given is not supported");
        }

        switch (queryApiObject.getQueryType()){
            case Constants.QUERY_TYPE_RANGE :
                return getMoodEntriesFromDateRange(queryApiObject.getStartDate(), queryApiObject.getEndDate(), appUser);
        }

        return null;
    }

    protected AppUserMoodEntriesApiObject getMoodEntriesFromDateRange(String startRange, String endRange, AppUser appUser){
        LocalDateTime startDate;
        LocalDateTime endDate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {

            startDate = LocalDate.parse(startRange, formatter).atTime(LocalTime.MIN);
            endDate = LocalDate.parse(endRange, formatter).atTime(LocalTime.MAX);
        }
        catch (Exception e){
            throw new BadRequestException("There was a problem with your inputted dates");
        }

        List<MoodEntryApiObject> moodEntryApiObjects =  moodEntryService.findByCreatedAtBetweenAndAppUser(startDate, endDate, appUser).stream().map(moodEntry -> new MoodEntryApiObject(moodEntry)).collect(Collectors.toList());
        return new AppUserMoodEntriesApiObject(moodEntryApiObjects, appUser.getMoodEntryStreak());
    }


    private void loadQueryTpyeHashSet(){
        queryTypes = new HashSet<>();
        queryTypes.add(Constants.QUERY_TYPE_RANGE);
    }
}
