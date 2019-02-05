package com.example.challenge.moodTracker.helpers.account;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.challenge.moodTracker.Constants;
import com.example.challenge.moodTracker.config.AppConfig;
import com.example.challenge.moodTracker.exceptions.custom.BadRequestException;
import com.example.challenge.moodTracker.helpers.statistics.StatisticHandler;
import com.example.challenge.moodTracker.integration.MessageProducer;
import com.example.challenge.moodTracker.integration.ProducerFactory;
import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.exceptions.custom.EntityNotFoundException;
import com.example.challenge.moodTracker.models.Statistic;
import com.example.challenge.moodTracker.security.JwtTokenUtil;
import com.example.challenge.moodTracker.services.interfaces.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountHandler {

    Logger logger = LoggerFactory.getLogger(AccountHandler.class);

    @Autowired protected AppUserService appUserService;

    @Autowired protected JwtTokenUtil jwtTokenUtil;

    @Autowired protected StatisticHandler statisticHandler;

    @Autowired(required = false) protected RedissonClient redissonClient;

    @Autowired protected ProducerFactory producerFactory;

    @Autowired protected PasswordEncoder passwordEncoder;

    @Autowired protected ObjectMapper objectMapper;

    protected void handleNewAccountSignUp(AccountApiObject requestObject){

        Optional <AppUser> savedAppUser = Optional.ofNullable(appUserService.findByEmailOrUsername(requestObject.getEmail(), requestObject.getUsername()));

        if(savedAppUser.isPresent()){
            throw new BadRequestException("The username and/or email is already taken");
        }
        AppUser appUser = new AppUser(requestObject.getUsername(), requestObject.getEmail(), passwordEncoder.encode(requestObject.getPassword()));
        appUserService.create(appUser);
        handleActiveUserStatistic();
        redissonClient.getMap("App_Users").put(appUser.getUsername(), appUser);

        try {

            getMessageProducer().sendMessage(new SendMessageRequest(getMessageProducer().getQueueUrl(), objectMapper.writeValueAsString(appUser)));
        }
        catch (Exception e)
        {
           logger.error("Could not send to SQS ", e);
        }
    }

    protected Object validateAndLoginUser(AccountApiObject requestObeject){
        Optional <AppUser> savedAppUser = Optional.ofNullable(appUserService.findByUsername(requestObeject.getUsername()));

        if(savedAppUser.isPresent() && validatePasswordMatch(requestObeject.getPassword(), savedAppUser.get().getPassword())){
            return jwtTokenUtil.generateToken(savedAppUser.get());
        }

        throw new EntityNotFoundException("No entity with userName " + requestObeject.getUsername());
    }

    public AccountApiObject getUserDetails(String userName){
        AppUser user;

        logger.info("Fetching appUser from redis");
        user = (AppUser) redissonClient.getMap("App_Users").get(userName);

        if (user == null){
            logger.info("AppUser not in cache ... fetching from database");
            user = appUserService.findByUsername(userName);

            if (user == null) {
                throw new EntityNotFoundException("Something went wrong looking for the AppUSer " + userName);
            }
            // Cache the appuser info
            redissonClient.getMap("App_Users").put(userName, user);
        }

        return new AccountApiObject.Builder(user.getUsername(), user.getEmail())
                .moodStreakEntry(user.getMoodEntryStreak())
                .lastMoodInputDate(user.getLastMoodInputDate())
                .build();
    }

    protected MessageProducer getMessageProducer(){
        return producerFactory.getProducer("App_Users");
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
