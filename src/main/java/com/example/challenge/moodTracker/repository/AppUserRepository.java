package com.example.challenge.moodTracker.repository;

import com.example.challenge.moodTracker.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    AppUser findByEmailAndUsername(String email, String username);

    AppUser findByEmailOrUsername(String email, String username);

    List<AppUser> findAllByOrderByMoodEntryStreakDesc();

}
