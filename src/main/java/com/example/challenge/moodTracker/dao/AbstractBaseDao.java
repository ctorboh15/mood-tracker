package com.example.challenge.moodTracker.dao;

import com.example.challenge.moodTracker.dao.interfaces.BaseDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseDao<T extends  Object> implements BaseDao<T> {
    @Override
    public T get(Serializable id) {
        Optional optional = getRepository().findById(id);
        if(optional.isPresent()){

            return (T) optional.get();
        }

        return null;
    }

    @Override
    public List<T> getAll() {
        List<T> results = new ArrayList<T>();
        getRepository().findAll().forEach(result -> results.add((T)result));
        return results;
    }

    @Override
    public void deleteById(Serializable id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(T t) {
        getRepository().delete(t);
    }

    @Override
    public void update(T t) {
        getRepository().save(t);
    }

    @Override
    public void create(T t) {
        getRepository().save(t);
    }

    @Override
    public boolean exists(Serializable id) {
        return getRepository().existsById(id);
    }

    public abstract JpaRepository getRepository();
}
