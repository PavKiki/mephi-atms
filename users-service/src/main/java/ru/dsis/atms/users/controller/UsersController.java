package ru.dsis.atms.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.users.dao.data.UserData;
import ru.dsis.atms.users.dao.data.UserRegistrationData;
import ru.dsis.atms.users.service.UsersService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/all")
    public List<UserData> findAll() {
        return usersService.findAll();
    }

    @GetMapping("/find/by/id")
    public UserData findById(@RequestParam Long id) {
        return usersService.findById(id);
    }

    @GetMapping("/find/by/username")
    public UserData findByUsername(@RequestParam String username) {
        return usersService.findByUsername(username);
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegistrationData user) {
        usersService.saveUser(user);
    }

    @PatchMapping("/patch/username/by/userId")
    public void patchUsername(@RequestParam Long userId, @RequestParam String newUsername) {
        usersService.patchUsernameByUserId(userId, newUsername);
    }

    @PatchMapping("/patch/username/by/username")
    public void patchUsername(@RequestParam String username, @RequestParam String newUsername) {
        usersService.patchUsernameByUsername(username, newUsername);
    }

    @PatchMapping("/patch/password/by/userId")
    public void patchPassword(@RequestParam Long userId, @RequestParam String newPassword) {
        usersService.patchPasswordByUserId(userId, newPassword);
    }

    @PatchMapping("/patch/password/by/username")
    public void patchPassword(@RequestParam String username, @RequestParam String newPassword) {
        usersService.patchPasswordByUsername(username, newPassword);
    }
    
    @PatchMapping("/patch/name/by/userId")
    public void patchName(@RequestParam Long userId, @RequestParam String newName) {
        usersService.patchNameByUserId(userId, newName);
    }
    
    @PatchMapping("/patch/name/by/username")
    public void patchName(@RequestParam String username, @RequestParam String newName) {
        usersService.patchNameByUsername(username, newName);
    }

    @PatchMapping("/patch/roleId/by/userId")
    public void patchRoleId(@RequestParam Long userId, @RequestParam Long newRoleId) {
        usersService.patchRoleByUserId(userId, newRoleId);
    }
    
    @PatchMapping("/patch/roleName/by/userId")
    public void patchRoleName(@RequestParam Long userId, @RequestParam String newRoleName) {
        usersService.patchRoleByUserId(userId, newRoleName);
    }

    @PatchMapping("/patch/roleId/by/username")
    public void patchRoleId(@RequestParam String username, @RequestParam Long newRoleId) {
        usersService.patchRoleByUsername(username, newRoleId);
    }

    @PatchMapping("/patch/roleName/by/username")
    public void patchRoleName(@RequestParam String username, @RequestParam String newRoleName) {
        usersService.patchRoleByUsername(username, newRoleName);
    }

    @DeleteMapping("/delete/by/userId")
    public void deleteUser(@RequestParam Long userId) {
        usersService.deleteById(userId);
    }

    @DeleteMapping("/delete/by/username")
    public void deleteUser(@RequestParam String username) {
        usersService.deleteByUsername(username);
    }
}
