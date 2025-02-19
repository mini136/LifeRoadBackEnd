package com.liferoad.liferoad_database_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final SecretKey SIGNING_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    private SecretKey getSigningKey() {
        return SIGNING_KEY;
    }

    // Generování JWT tokenu
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 1 hodina
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrakce emailu z tokenu
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Získání expirace tokenu
    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    // Kontrola, zda token je expirovaný
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validace tokenu
    public boolean validateToken(String token, String email) {
        try {
            return (email.equals(extractEmail(token)) && !isTokenExpired(token));
        } catch (JwtException e) {
            return false;
        }
    }

    // Získání Claims (dat z tokenu)
    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}
