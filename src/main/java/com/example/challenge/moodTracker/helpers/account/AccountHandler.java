package com.example.challenge.moodTracker.helpers.account;

import com.example.challenge.moodTracker.Constants;
import com.example.challenge.moodTracker.exceptions.custom.BadRequestException;
import com.example.challenge.moodTracker.helpers.statistics.StatisticHandler;
import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.exceptions.custom.EntityNotFoundException;
import com.example.challenge.moodTracker.models.Statistic;
import com.example.challenge.moodTracker.security.JwtTokenUtil;
import com.example.challenge.moodTracker.services.interfaces.AppUserService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountHandler {

    @Autowired
    protected AppUserService appUserService;

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Autowired
    protected StatisticHandler statisticHandler;

    @Autowired RedissonClient redissonClient;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected void handleNewAccountSignUp(AccountApiObject requestObject){

        Optional <AppUser> savedAppUser = Optional.ofNullable(appUserService.findByEmailOrUsername(requestObject.getEmail(), requestObject.getUsername()));

        if(savedAppUser.isPresent()){
            throw new BadRequestException("The username and/or email is already taken");
        }
        AppUser appUser = new AppUser(requestObject.getUsername(), requestObject.getEmail(), passwordEncoder.encode(requestObject.getPassword()));
        appUserService.create(appUser);
        handleActiveUserStatistic();
        redissonClient.getMap("App_Users").put(appUser.getUsername(), appUser);
    }

    protected Object validateAndLoginUser(AccountApiObject requestObeject){
        Optional <AppUser> savedAppUser = Optional.ofNullable(appUserService.findByUsername(requestObeject.getUsername()));

        if(savedAppUser.isPresent() && validatePasswordMatch(requestObeject.getPassword(), savedAppUser.get().getPassword())){
            return jwtTokenUtil.generateToken(savedAppUser.get());
        }

        throw new EntityNotFoundException("No entity with userName " + requestObeject.getUsername());
    }

    protected void handleActiveUserStatistic(){
        Statistic statistic = statisticHandler.getStatistic(Constants.STATISTIC_ACTIVE_APP_USERS_COUNT);
        if(statistic != null){
            statisticHandler.incrementStatistic(Constants.STATISTIC_ACTIVE_APP_USERS_COUNT);
        }
        else {
            statisticHandler.createStatistic(Constants.STATISTIC_ACTIVE_APP_USERS_COUNT);
            statisticHandler.incrementStatistic(Constants.STATISTIC_ACTIVE_APP_USERS_COUNT);
        }
    }



    public void doNewAccountSignUp(AccountApiObject requestObject){
        handleNewAccountSignUp(requestObject);
    }

    public Object doUserLogin(AccountApiObject requestObject){
        return validateAndLoginUser(requestObject);
    }

    private boolean validatePasswordMatch(String given, String stored){
        return passwordEncoder.matches(given, stored);
    }



}
