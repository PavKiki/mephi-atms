package ru.dsis.atms.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ru.dsis.atms.auth.dao.UserInfo;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class JwtProvider {
    private long expirationTime;
    private String secret;

    public JwtProvider(long expirationTime, String secret) {
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    public String generateToken(UserInfo userInfo) {
        var claims = Jwts.claims();
        claims.put("name", userInfo.getName());
        claims.put("role", userInfo.getRole());
        claims.put("created", userInfo.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return Jwts.builder()
                   .setSubject(userInfo.getUsername())
                   .setClaims(claims)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }
}
