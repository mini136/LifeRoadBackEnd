package com.liferoad.liferoad_database_api.service;

import com.liferoad.liferoad_database_api.model.Mood;
import com.liferoad.liferoad_database_api.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodService {

    @Autowired
    private MoodRepository moodRepository;

    public void saveUserMood(String email, String moodText) {
        Mood mood = new Mood();
        mood.setUserEmail(email);  // Použití e-mailu místo userId
        mood.setMood(moodText);
        moodRepository.save(mood);
    }

    public List<Mood> getUserMoods(String email) {
        return moodRepository.findByUserEmail(email);
    }
}
