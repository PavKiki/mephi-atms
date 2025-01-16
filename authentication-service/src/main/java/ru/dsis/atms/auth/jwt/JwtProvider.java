package ru.dsis.atms.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dsis.atms.auth.dao.UserInfo;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private final Logger log = LoggerFactory.getLogger(JwtProvider.class);

    private final long expirationTime;
    private final SecretKey secretKey;

    public JwtProvider(long expirationTime, String secret) {
        this.expirationTime = expirationTime;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserInfo userInfo) {
        return Jwts.builder()
                   .subject(userInfo.getUsername())
                   .claim("name", userInfo.getName())
                   .claim("role", userInfo.getRole())
                   .claim("created", userInfo.getCreated().toString())
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(secretKey)
                   .compact();
    }

    public Claims validateToken(String token) {
        try {
            var jwtParser = Jwts.parser()
                                .verifyWith(secretKey)
                                .decryptWith(secretKey)
                                .build();

            var payload = jwtParser.parseSignedClaims(token).getPayload();
            if (payload.getExpiration().before(new Date())) {
                log.error("Token expired");
                throw new IllegalStateException("Token expired");
            }

            return payload;
        } catch (Exception e) {
            log.error("Invalid token ", e);
            return null;
        }
    }
}
