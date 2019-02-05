package com.example.challenge.moodTracker.repositories;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.models.MoodEntry;
import com.example.challenge.moodTracker.models.MoodType;
import com.example.challenge.moodTracker.repository.AppUserRepository;
import com.example.challenge.moodTracker.repository.MoodEntryRepository;
import com.example.challenge.moodTracker.repository.MoodTypeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MoodEntryRepoTest extends BaseModelTestClass<MoodEntry, MoodEntryRepository> {

    @Autowired
    protected MoodEntryRepository moodEntryRepository;

    @Autowired
    protected AppUserRepository appUserRepository;

    @Autowired
    protected MoodTypeRepository moodTypeRepository;


    AppUser appUser;
    AppUser appUser1;
    AppUser appUser2;

    @Before
    public void before(){
        super.before();
        createDefaultAppUsers();
    }
    @Override
    protected MoodEntryRepository getRepository() {
        return moodEntryRepository;
    }

    @Test
    public void testFindAllByAppUser_EntriesFound(){
        int GENERATED_MOODTYPES_COUNT = 4;
        // Setup
        createAppUserAndMoodEntries(GENERATED_MOODTYPES_COUNT);

        // Method Call
        List<MoodEntry> moodEntries =  moodEntryRepository.findByAppUser(appUser);

        // Verify
        Assert.assertEquals(GENERATED_MOODTYPES_COUNT, moodEntries.size());
    }


    @Test
    public void testFindByMoodType_EntriesFound(){

        // Setup
        List<MoodType> moodTypes = generateMoodTypes(1);
        moodTypeRepository.saveAll(moodTypes);
        moodEntryRepository.save(new MoodEntry(appUser, moodTypes.get(0)));
        moodEntryRepository.save(new MoodEntry(appUser1, moodTypes.get(0)));
        moodEntryRepository.save(new MoodEntry(appUser2, moodTypes.get(0)));

        // Method Call
        List<MoodEntry> returnedEntries = moodEntryRepository.findByMoodType(moodTypes.get(0));


        // Verify
        Assert.assertEquals(3, returnedEntries.size());
    }


    protected void createAppUserAndMoodEntries(int moodEntries){
        List<MoodType> moodTypes = generateMoodTypes(moodEntries);

        moodTypes.forEach(type -> {
            moodEntryRepository.save(new MoodEntry(appUser, type));
        });
    }

    protected void createDefaultAppUsers(){
        appUser = AppUserRepoTest.createAppUser(AppUserRepoTest.APP_USER_USERNAME, AppUserRepoTest.APP_USER_PASSWORD, AppUserRepoTest.APP_USER_EMAIL);
        appUser1 = AppUserRepoTest.createAppUser(AppUserRepoTest.APP_USER_USERNAME + PLUS_ONE_APPENDER, AppUserRepoTest.APP_USER_PASSWORD + PLUS_ONE_APPENDER, AppUserRepoTest.APP_USER_EMAIL + PLUS_ONE_APPENDER);
        appUser2 = AppUserRepoTest.createAppUser(AppUserRepoTest.APP_USER_USERNAME + PLUS_TWO_APPENDER, AppUserRepoTest.APP_USER_PASSWORD + PLUS_TWO_APPENDER, AppUserRepoTest.APP_USER_EMAIL + PLUS_TWO_APPENDER);
        appUserRepository.save(appUser);
        appUserRepository.save(appUser1);
        appUserRepository.save(appUser2);
    }

    protected List<MoodType> generateMoodTypes(int numberToGenerate){
        List<MoodType> moodTypes = MoodTypeRepoTest.generateMoodTypes(numberToGenerate);
        moodTypeRepository.saveAll(moodTypes);
        return moodTypes;
    }
}
