package com.liferoad.liferoad_database_api.repository;

import com.liferoad.liferoad_database_api.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MoodRepository extends JpaRepository<Mood, Long> {
    List<Mood> findByUserEmail(String email);  // Změna z userId na email
}
