package com.example.challenge.moodTracker.helpers.moodentry;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import com.example.challenge.moodTracker.services.interfaces.AppUserService;
import com.example.challenge.moodTracker.services.interfaces.MoodEntryService;
import com.example.challenge.moodTracker.services.interfaces.MoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class MoodEntryHandler {

    @Autowired
    private MoodEntryService moodEntryService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MoodTypeService moodTypeService;

    public void doMoodEntry(MoodEntryApiObject moodEntryApiObject, AppUser user){
        createNewMoodEntry(moodEntryApiObject.getDesc(), user);
    }

    protected void createNewMoodEntry(String description, AppUser user){
        Optional<MoodType> savedMoodType = Optional.ofNullable(moodTypeService.findMoodByDescription(description));

        if(savedMoodType.isPresent()){
            saveNewMoodEntryBean(savedMoodType.get(), user);
        }
        else {
            MoodType newMoodTypeBean = new MoodType(description);
            moodTypeService.create(newMoodTypeBean);
            saveNewMoodEntryBean(newMoodTypeBean, user);
        }
    }

    protected void saveNewMoodEntryBean(MoodType moodType, AppUser appUser){
        MoodEntry moodEntry = new MoodEntry(appUser, moodType);
        moodEntryService.create(moodEntry);
        handleAppUserStreakUpdate(appUser, moodEntry);
    }

    protected void handleAppUserStreakUpdate(AppUser appUser, MoodEntry moodEntry){
        LocalDateTime currentMoodEntryDateTime = moodEntry.getCreatedAt();
        LocalDateTime dayBefore = currentMoodEntryDateTime.minusDays(1);


        Optional<LocalDateTime> appUserLastInputDate =  Optional.ofNullable(appUser.getLastMoodInputDate());

        if(appUserLastInputDate.isPresent()){
            LocalDateTime lastMoodInputDate = appUserLastInputDate.get();
            int currentStreak = appUser.getMoodEntryStreak();

            // if the last mood input fell in between yesterday
            if(lastMoodInputDate.isAfter(dayBefore.with(LocalDateTime.MIN)) && lastMoodInputDate.isBefore(dayBefore.with(LocalDateTime.MAX))){
                appUser.setMoodEntryStreak(++currentStreak);
            }

            // if the last input was made today do nothing
            else if (lastMoodInputDate.isAfter(currentMoodEntryDateTime.with(LocalDateTime.MIN)) && lastMoodInputDate.isBefore(currentMoodEntryDateTime.with(LocalDateTime.MAX))){
                // Do nothing as we only increment the streak on a daily basis.
            }
            else {
                appUser.setMoodEntryStreak(1);
            }
        }
        else {
            appUser.setMoodEntryStreak(1);
        }

        appUser.setLastMoodInputDate(moodEntry.getCreatedAt());
        appUserService.update(appUser);
    }


    protected List<MoodEntry> findMoodEntriesByAppUser(AppUser appUser){
       return moodEntryService.findByAppUser(appUser);
    }
}
