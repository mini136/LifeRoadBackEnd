package com.liferoad.liferoad_database_api.service;

import com.liferoad.liferoad_database_api.dto.AuthResponse;
import com.liferoad.liferoad_database_api.dto.AuthRequest;
import com.liferoad.liferoad_database_api.model.User;
import com.liferoad.liferoad_database_api.repository.UserRepository;
import com.liferoad.liferoad_database_api.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(AuthRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(null, request.getEmail(), hashedPassword, request.getName());
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail());
                return new AuthResponse(token);
            }
        }
        throw new RuntimeException("Invalid email or password!");
    }
}
