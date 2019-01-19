package com.example.challenge.moodTracker.services;

import com.example.challenge.moodTracker.models.AppUser;
import com.example.challenge.moodTracker.dao.interfaces.AppUserDao;
import com.example.challenge.moodTracker.dao.interfaces.BaseDao;
import com.example.challenge.moodTracker.services.interfaces.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl extends AbstractBaseService<AppUser> implements AppUserService {

    @Autowired
    AppUserDao appUserDao;

    @Override
    protected BaseDao<AppUser> getDAO() {
        return appUserDao;
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserDao.findByUserName(username);
    }

    @Override
    public AppUser findByEmail(String email) {
        return appUserDao.findByEmail(email);
    }

    @Override
    public AppUser findByEmailAndUsername(String email, String username) {
        return appUserDao.findByEmailAndUsername(email, username);
    }

    @Override
    public AppUser findByEmailOrUsername(String email, String username) {
        return null;
    }

    @Override
    public List<AppUser> findAllByOrderByMoodEntryStreakDesc() {
        return appUserDao.findAllByOrderByMoodEntryStreakDesc();
    }
}