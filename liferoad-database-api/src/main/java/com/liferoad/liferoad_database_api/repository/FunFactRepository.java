package com.liferoad.liferoad_database_api.repository;

import com.liferoad.liferoad_database_api.model.FunFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunFactRepository extends JpaRepository<FunFact, Long> {
}
