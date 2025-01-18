package ru.dsis.atms.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.auth.dao.TokenRoleDto;
import ru.dsis.atms.auth.dao.UserDao;
import ru.dsis.atms.auth.jwt.JwtProvider;
import ru.dsis.atms.auth.repository.AuthRepository;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public TokenRoleDto auth(UserDao userDao) {
        var user = authRepository.findByUserCredentials(userDao);
        if (user == null) {
            throw new IllegalArgumentException("User with this credentials not found!");
        }

        var token = jwtProvider.generateToken(user);
        authRepository.saveToken(user, token);

        var tokenRoleDto = new TokenRoleDto();
        tokenRoleDto.setToken(token);
        tokenRoleDto.setRole(user.getRole());
        return tokenRoleDto;
    }

    public String generatePublicApiToken() {
        return jwtProvider.generatePublicApiToken();
    }

    public boolean validateToken(String token) {
        var claims = jwtProvider.validateToken(token);
        if (claims != null) {
            return authRepository.tokenExistsByUsername(claims.getSubject(), token);
        } else {
            return false;
        }
    }

    public boolean validatePublicApiToken(String token) {
        var claims = jwtProvider.validatePublicApiToken(token);
        return claims != null;
    }
}