package ru.dsis.atms.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dsis.atms.auth.dao.UserDto;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private final Logger log = LoggerFactory.getLogger(JwtProvider.class);

    private final long expirationTime;
    private final SecretKey secretKey;
    private final SecretKey publicApiSecretKey;

    public JwtProvider(long expirationTime, String secret, String publicApiSecret) {
        this.expirationTime = expirationTime;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.publicApiSecretKey = Keys.hmacShaKeyFor(publicApiSecret.getBytes());;
    }

    public String generateToken(UserDto userDto) {
        return Jwts.builder()
                   .subject(userDto.getUsername())
                   .claim("name", userDto.getName())
                   .claim("role", userDto.getRole())
                   .claim("created", userDto.getCreated().toString())
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(secretKey)
                   .compact();
    }

    public String generatePublicApiToken() {
        return Jwts.builder()
                   .subject("Public token")
                   .issuedAt(new Date())
                   .expiration(new Date(Long.MAX_VALUE))
                   .signWith(secretKey)
                   .compact();
    }

    public Claims validatePublicApiToken(String token) {
        try {
            var jwtParser = Jwts.parser()
                                .verifyWith(secretKey)
                                .decryptWith(secretKey)
                                .build();

            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            log.error("Invalid public api token ", e);
            return null;
        }
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
