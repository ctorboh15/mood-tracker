package com.example.challenge.moodTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication()
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class MoodTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodTrackerApplication.class, args);
	}
}
