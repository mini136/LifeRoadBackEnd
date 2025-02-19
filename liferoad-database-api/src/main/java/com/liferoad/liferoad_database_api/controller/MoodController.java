package com.liferoad.liferoad_database_api.controller;

import com.liferoad.liferoad_database_api.dto.MoodRequest;
import com.liferoad.liferoad_database_api.model.Mood;
import com.liferoad.liferoad_database_api.security.JwtUtil;
import com.liferoad.liferoad_database_api.service.MoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mood")
public class MoodController {

    private final MoodService moodService;
    private final JwtUtil jwtUtil;

    // Konstruktor pro injektování MoodService a JwtUtil
    public MoodController(MoodService moodService, JwtUtil jwtUtil) {
        this.moodService = moodService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<String> saveMood(@RequestBody MoodRequest moodRequest, HttpServletRequest request) {
        Optional<String> tokenOpt = extractToken(request);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }

        String token = tokenOpt.get();
        String email = jwtUtil.extractEmail(token);

        if (!jwtUtil.validateToken(token, email)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        moodService.saveUserMood(email, moodRequest.getMood());
        return ResponseEntity.ok("Mood uložen!");
    }

    @GetMapping
    public ResponseEntity<List<Mood>> getUserMoods(HttpServletRequest request) {
        Optional<String> tokenOpt = extractToken(request);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(401).body(null);
        }

        String token = tokenOpt.get();
        String email = jwtUtil.extractEmail(token);

        if (!jwtUtil.validateToken(token, email)) {
            return ResponseEntity.status(401).body(null);
        }

        List<Mood> moods = moodService.getUserMoods(email);
        return ResponseEntity.ok(moods);
    }

    // Metoda pro extrakci tokenu z HTTP hlavičky
    private Optional<String> extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}

