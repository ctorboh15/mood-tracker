package com.example.challenge.moodTracker.dao;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.dao.interfaces.AppUserDao;
import com.example.challenge.moodTracker.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppUserDaoImpl extends AbstractBaseDao<AppUser> implements AppUserDao {

    @Autowired
    private AppUserRepository repository;

    @Override
    public AppUser findByUserName(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public AppUser findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public AppUser findByEmailAndUsername(String email, String username) {
        return repository.findByEmailAndUsername(email, username);
    }

    @Override
    public AppUser findByEmailOrUsername(String email, String username) {
        return repository.findByEmailOrUsername(email, username);
    }

    @Override
    public List<AppUser> findAllByOrderByMoodEntryStreakDesc() {
        return repository.findAllByOrderByMoodEntryStreakDesc();
    }

    @Override
    public JpaRepository getRepository() {
        return repository;
    }
}
