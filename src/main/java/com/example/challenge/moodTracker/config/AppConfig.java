package com.example.challenge.moodTracker.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.example.challenge.moodTracker.models.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.KryoCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.swing.plaf.synth.Region;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class AppConfig {
    Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${REDIS_URL:redis://127.0.0.1:6379}")
    private String REDIS_URL;

    @Value("${AWS_ACCESS_KEY_ID}")
    private String AWS_ACCESS_KEY_ID;

    @Value("${AWS_SECRET_ACCESS_KEY}")
    private String AWS_SECRET_ACCESS_KEY;

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        return loggingFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(new KryoCodec(Arrays.asList(AppUser.class, LocalDateTime.class, String.class)))
                .useSingleServer().setAddress(REDIS_URL);
        RedissonClient redisson = null;
        try {
            redisson  = Redisson.create(config);
        } catch (Exception e)
        {
            logger.error("Failed to create Redisson Client :: " + e.getLocalizedMessage());
            logger.error("Redisson Client Exception", e);
        }
        return redisson;
    }


    protected AWSCredentials credentials(String keyId, String secretKey){
        return new BasicAWSCredentials(keyId, secretKey);
    }

    @Bean
    public AmazonSQS amazonSQS(){
        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY)))
                .withRegion(Regions.US_EAST_2)
                .build();

        return sqs;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}