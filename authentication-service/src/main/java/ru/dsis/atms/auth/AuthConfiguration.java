package ru.dsis.atms.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dsis.atms.auth.cookie.CookieProvider;
import ru.dsis.atms.auth.jwt.JwtProvider;

@Configuration
public class AuthConfiguration {
    @Value("${auth.service.jwt.secret.key}")
    private String secretKey;

    @Value("${auth.service.jwt.expiration.time}")
    private long expirationTime;

    @Value("${auth.service.jwt.cookie.age}")
    private int cookieAge;

    @Value("${auth.service.jwt.cookie.httpOnly}")
    private boolean isHttpOnly;

    @Value("${auth.service.jwt.cookie.secure}")
    private boolean isSecure;

    @Bean
    public CookieProvider cookieProvider() {
        return new CookieProvider(cookieAge, isHttpOnly, isSecure);
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(expirationTime, secretKey);
    }
}
