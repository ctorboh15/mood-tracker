package com.example.challenge.moodTracker.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="AppUser")
public class AppUser extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AppUserID")
    private long appUserId;

    @Column(name = "Username", length = 50, unique = true)
    @NotNull(message = "{person.username.notNull}")
    @Size(min = 4, max = 25)
    private String username;

    @Column(name = "Password", length = 100)
    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    @Column(name = "Email", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "MoodEntryStreak")
    private int moodEntryStreak;

    @Column(name = "LastMoodInputDate", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastMoodInputDate;

    public AppUser() {
        this.moodEntryStreak = 0;
    }

    public AppUser(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(long appUserId) {
        this.appUserId = appUserId;
    }

    public int getMoodEntryStreak() {
        return moodEntryStreak;
    }

    public void setMoodEntryStreak(int moodEntryStreak) {
        this.moodEntryStreak = moodEntryStreak;
    }

    public LocalDateTime getLastMoodInputDate() {
        return lastMoodInputDate;
    }

    public void setLastMoodInputDate(LocalDateTime lastMoodInputDate) {
        this.lastMoodInputDate = lastMoodInputDate;
    }
}
