package ru.dsis.atms.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.users.dao.PostgresUser;
import ru.dsis.atms.users.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/all")
    public List<PostgresUser> findAll() {
        return usersService.getAllUsers();
    }

    @PostMapping("/register")
    public void registerUser(@RequestParam PostgresUser user) {
        usersService.saveUser(user);
    }
}
