package com.liferoad.liferoad_database_api.controller;

import com.liferoad.liferoad_database_api.model.FunFact;
import com.liferoad.liferoad_database_api.service.FunFactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funfacts")
@CrossOrigin(origins = "*") // Povolení CORS pro front-end aplikace
public class FunFactController {
    private final FunFactService funFactService;

    public FunFactController(FunFactService funFactService) {
        this.funFactService = funFactService;
    }

    // Endpoint pro získání všech FunFact objektů
    @GetMapping
    public ResponseEntity<List<FunFact>> getAllFunFacts() {
        return ResponseEntity.ok(funFactService.getAllFunFacts());
    }

    // Endpoint pro uložení nového FunFact
    @PostMapping
    public ResponseEntity<FunFact> createFunFact(@RequestBody FunFact funFact) {
        return ResponseEntity.ok(funFactService.saveFunFact(funFact));
    }
}
