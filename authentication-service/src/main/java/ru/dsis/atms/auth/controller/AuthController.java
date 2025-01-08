package ru.dsis.atms.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
