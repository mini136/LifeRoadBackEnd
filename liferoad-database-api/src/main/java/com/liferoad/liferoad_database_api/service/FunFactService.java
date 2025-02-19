package com.liferoad.liferoad_database_api.service;

import com.liferoad.liferoad_database_api.model.FunFact;
import com.liferoad.liferoad_database_api.repository.FunFactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunFactService {
    private final FunFactRepository funFactRepository;

    public FunFactService(FunFactRepository funFactRepository) {
        this.funFactRepository = funFactRepository;
    }

    public List<FunFact> getAllFunFacts() {
        return funFactRepository.findAll();
    }

    public FunFact saveFunFact(FunFact funFact) {
        return funFactRepository.save(funFact);
    }
}
