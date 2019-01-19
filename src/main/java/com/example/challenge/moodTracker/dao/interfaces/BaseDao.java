package com.example.challenge.moodTracker.dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T extends Object> {
    T get(Serializable id);
    List<T> getAll();
    void deleteById(Serializable id);
    void delete(T t);
    void update(T t);
    void create(T t);
    boolean exists(Serializable id);
}
