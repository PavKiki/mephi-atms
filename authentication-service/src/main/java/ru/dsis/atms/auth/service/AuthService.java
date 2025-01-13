package ru.dsis.atms.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.auth.dao.UserDao;
import ru.dsis.atms.auth.jwt.JwtProvider;
import ru.dsis.atms.auth.repository.AuthRepository;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public String auth(UserDao userDao) {
        var user = authRepository.findByUserCredentials(userDao);
        if (user == null) {
            throw new IllegalArgumentException("User with this credentials not found!");
        }

        return jwtProvider.generateToken(user);
    }

    public boolean validateToken(String token) {
        return jwtProvider.validateToken(token);
    }
}
