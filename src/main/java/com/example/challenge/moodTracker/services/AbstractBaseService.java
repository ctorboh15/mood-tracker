package com.example.challenge.moodTracker.services;

import com.example.challenge.moodTracker.dao.interfaces.BaseDao;
import com.example.challenge.moodTracker.services.interfaces.BaseServiceInterface;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractBaseService<T extends Object> implements BaseServiceInterface<T> {

    public void create(T t){
        getDAO().create(t);
    }

    public List<T> getAll(){
        return getDAO().getAll();
    }

    public T get(Serializable id){
        return getDAO().get(id);
    }

    public void update(T t){
        getDAO().update(t);
    }

    public void deleteById(Serializable id){
        getDAO().deleteById(id);
    }

    public void delete(T t){
        getDAO().delete(t);
    }

    public boolean exists(Serializable id){
        return getDAO().exists(id);
    }

    protected abstract BaseDao<T> getDAO();
}
