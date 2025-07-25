package com.example.library.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 🔑 Khoá bí mật (nên lưu trong biến môi trường khi production)
    private static final String JWT_SECRET = "my-super-secret-key-for-jwt-token-generation-must-be-at-least-256-bits";

    // ⏰ Thời hạn token (1 ngày)
    private static final long JWT_EXPIRATION_MS = 24 * 60 * 60 * 1000;

    // 🔐 Tạo SecretKey từ chuỗi bí mật
    private final SecretKey secretKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    /** Sinh JWT từ username */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    /** Lấy username từ token */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /** Kiểm tra token hợp lệ */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            System.err.println("Invalid JWT token: " + ex.getMessage());
            return false;
        }
    }
}