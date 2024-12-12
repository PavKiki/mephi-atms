package ru.dsis.atms.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.users.dao.PostgresUser;
import ru.dsis.atms.users.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/all")
    public ResponseEntity<String> findAll() {
        return ResponseEntity.ok(usersService.findAll().toString());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestParam String login, @RequestParam String password, @RequestParam Long roleId) {
        usersService.addUser(PostgresUser.of(login, password, roleId));
        return ResponseEntity.ok("Success!");
    }
}
