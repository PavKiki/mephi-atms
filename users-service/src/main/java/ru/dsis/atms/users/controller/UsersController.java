package ru.dsis.atms.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.users.dao.data.UserData;
import ru.dsis.atms.users.dao.postgres.PostgresUser;
import ru.dsis.atms.users.dao.data.UserDataWithPassword;
import ru.dsis.atms.users.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/all")
    public List<UserData> findAll() {
        return usersService.getAllUsers();
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserDataWithPassword user) {
        usersService.saveUser(user);
    }

    @PatchMapping("/addRole")
    public void addRole(@RequestParam String user, @RequestParam String role) {
        usersService.addRoleToUser(user, role);
    }
}
