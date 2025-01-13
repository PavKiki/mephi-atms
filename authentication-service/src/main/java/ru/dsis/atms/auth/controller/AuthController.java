package ru.dsis.atms.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.auth.cookie.CookieProvider;
import ru.dsis.atms.auth.dao.UserDao;
import ru.dsis.atms.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    CookieProvider cookieProvider;

    @PostMapping("/login")
    public void login(@RequestBody UserDao userDto, HttpServletResponse response) {
        var token = authService.auth(userDto);
        var cookie = cookieProvider.getCookie(token);
        response.addCookie(cookie);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        var cookie = cookieProvider.emptyCookie();
        response.addCookie(cookie);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (authService.validateToken(token)) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
