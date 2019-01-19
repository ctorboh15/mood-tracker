package com.example.challenge.moodTracker.repositories;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.repository.AppUserRepository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;


public class AppUserRepoTestClass extends BaseModelTestClass<AppUser, AppUserRepository> {

    @Autowired protected AppUserRepository appUserRepository;

    protected String APP_USER_USERNAME = "AppUserName1";
    protected String APP_USER_PASSWORD = "Password";
    protected String APP_USER_EMAIL = "email@email.com";


    @Override
    protected AppUserRepository getRepository() {
        return appUserRepository;
    }


    @Test(expected = ConstraintViolationException.class)
    public void testUsernameNotNullConstraint(){
        // Method Call
        saveEntity(createAppUser(null, APP_USER_PASSWORD, APP_USER_EMAIL));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testPasswordNotNullConstraint(){
        // Method Call
        saveEntity(createAppUser(APP_USER_USERNAME, null, APP_USER_EMAIL));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testEmailNotNullConstraint(){
        // Method Call
        saveEntity(createAppUser(APP_USER_USERNAME, APP_USER_PASSWORD, null));
    }

    @Test
    public void tesFindAppUserByUserName_UserFound(){
        // Method Call
        saveEntity(createAppUser(APP_USER_USERNAME,APP_USER_PASSWORD, APP_USER_EMAIL));
        AppUser returned = getRepository().findByUsername(APP_USER_USERNAME);

        // Verify
        Assert.assertNotNull(returned);
    }

    @Test
    public void testFindAppUserByEmailOrUserName_UserFoundBothWays(){
        // Set Up
        saveEntity(createAppUser(APP_USER_USERNAME,APP_USER_PASSWORD, APP_USER_EMAIL));

        // Method Call
        AppUser returnedByUserName = getRepository().findByEmailOrUsername("",APP_USER_USERNAME);
        AppUser returnedByEmail = getRepository().findByEmailOrUsername(APP_USER_EMAIL,"");

        // Verify
        Assert.assertEquals(returnedByEmail.getAppUserId(), returnedByUserName.getAppUserId());
    }

    @Test
    public void testFindAllByOrderMoodEntryStreakDesc(){
        String PLUS_ONE_APPENDER = "-1";
        String PLUS_TWO_APPENDER = "-2";

        saveEntity(createAppUser(APP_USER_USERNAME,APP_USER_PASSWORD, APP_USER_EMAIL, 4));
        saveEntity(createAppUser(APP_USER_USERNAME + PLUS_ONE_APPENDER ,APP_USER_PASSWORD + PLUS_ONE_APPENDER, APP_USER_EMAIL + PLUS_ONE_APPENDER, 6));
        saveEntity(createAppUser(APP_USER_USERNAME + PLUS_TWO_APPENDER ,APP_USER_PASSWORD + PLUS_TWO_APPENDER, APP_USER_EMAIL + PLUS_TWO_APPENDER));

        List<AppUser> users = getRepository().findAllByOrderByMoodEntryStreakDesc();

        Assert.assertEquals(3, users.size());
        Assert.assertEquals(users.get(0).getUsername(), APP_USER_USERNAME + PLUS_ONE_APPENDER);
        Assert.assertEquals(users.get(1).getUsername(), APP_USER_USERNAME);
        Assert.assertEquals(users.get(2).getUsername(), APP_USER_USERNAME + PLUS_TWO_APPENDER);
    }

    ////////////////////////////// Helpers //////////////////////////////////

    protected AppUser createAppUser(String username, String password, String email){
        return new AppUser(username, email, password);
    }

    protected AppUser createAppUser(String username, String password, String email, int moodEntryStreak){
            AppUser user = createAppUser(username,password,email);
            user.setMoodEntryStreak(moodEntryStreak);
            return  user;
    }
}
