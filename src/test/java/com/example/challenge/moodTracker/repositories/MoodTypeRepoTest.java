package com.example.challenge.moodTracker.repositories;

import com.example.challenge.moodTracker.models.MoodType;
import com.example.challenge.moodTracker.repository.MoodTypeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoodTypeRepoTest extends BaseModelTestClass<MoodType, MoodTypeRepository> {
    @Autowired
    protected MoodTypeRepository moodTypeRepository;


    @Override
    protected MoodTypeRepository getRepository() {
        return moodTypeRepository;
    }

    @Test
    public void testFindMoodTypeByDesc_MoodTypeFound(){
        // Setup
        getRepository().saveAll(generateMoodTypes(2));

        // Method Call
        MoodType foundMoodType = getRepository().findMoodTypeByDescription("Desc 1");

        // Verify
        Assert.assertEquals("Desc 1", foundMoodType.getDescription());

    }

    public static List<MoodType> generateMoodTypes(int numberOfEntries){
       return IntStream.range(0,numberOfEntries).boxed().map(i -> new MoodType("Desc " +  i))
                .collect(Collectors.toList());
    }


}