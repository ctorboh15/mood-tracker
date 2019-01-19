package com.example.challenge.moodTracker.services.interfaces;

import java.io.Serializable;
import java.util.List;

public interface BaseServiceInterface<T extends Object> {
    void create(T t);
    T get(Serializable id);
    List<T> getAll();
    void update(T t);
    void deleteById(Serializable id);
    void delete(T t);
    boolean exists(Serializable id);
}