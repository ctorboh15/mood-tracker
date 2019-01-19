package com.example.challenge.moodTracker.repositories;


import com.example.challenge.moodTracker.models.AbstractModel;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class BaseModelTestClass<M extends AbstractModel, R extends JpaRepository> {

    @Before
    public void before(){
        clearRepository();
    }

    protected abstract R getRepository();

    protected void saveEntity(M m){
        getRepository().save(m);
    }

    protected void deleteById(Long id){
        getRepository().deleteById(id);
    }

    protected void delete(M m){
        getRepository().delete(m);
    }

    protected void clearRepository(){
        getRepository().deleteAll();
    }

}
