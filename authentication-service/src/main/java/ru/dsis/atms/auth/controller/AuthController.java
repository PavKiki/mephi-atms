package ru.dsis.atms.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.auth.dao.UserDao;
import ru.dsis.atms.auth.service.AuthService;
import ru.dsis.atms.util.web.WebUtils.Response;
import ru.dsis.atms.util.web.WebUtils.TokenResponse;
import ru.dsis.atms.util.web.WebUtils.ErrorResponse;
import ru.dsis.atms.util.web.WebUtils.SuccessResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody UserDao userDto, HttpServletResponse response) {
        try {
            var tokenRoleDto = authService.auth(userDto);
            return new ResponseEntity<>(new TokenResponse(tokenRoleDto.getToken(), tokenRoleDto.getRole()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Response> validateToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (authService.validateToken(token)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ErrorResponse("Invalid token"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/token/public")
    public ResponseEntity<Response> getPublicToken() {
        return new ResponseEntity<>(new SuccessResponse(authService.generatePublicApiToken()), HttpStatus.OK);
    }

    @GetMapping("/validate/public")
    public ResponseEntity<Response> validatePublicApiToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (authService.validatePublicApiToken(token)) {
                return new ResponseEntity<>(new SuccessResponse("Validated successfully"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ErrorResponse("Invalid token"), HttpStatus.UNAUTHORIZED);
    }
}
