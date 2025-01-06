package ru.dsis.atms.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.users.dao.postgres.PostgresPermission;
import ru.dsis.atms.users.service.PermissionsService;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionsController {
    @Autowired
    private PermissionsService permissionsService;

    @PostMapping("/register")
    public void register(@RequestBody PostgresPermission permission) {
        permissionsService.savePermission(permission);
    }

    @GetMapping("/all")
    public List<PostgresPermission> findAll() {
        return permissionsService.findAll();
    }

    @GetMapping("/findBy")
    public List<PostgresPermission> findByUserId(@RequestParam Long userId) {
        return permissionsService.findByUserId(userId);
    }
}
