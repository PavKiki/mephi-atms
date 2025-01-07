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

    @GetMapping("/all")
    public List<PostgresPermission> findAll() {
        return permissionsService.findAll();
    }

    @GetMapping("/find/by/permissionId")
    public PostgresPermission findById(@RequestParam Long id) {
        return permissionsService.findById(id);
    }

    @GetMapping("/find/by/permissionName")
    public PostgresPermission findByName(@RequestParam String name) {
        return permissionsService.findByName(name);
    }

    @GetMapping("/find/by/roleId")
    public List<PostgresPermission> findByRoleId(@RequestParam Long roleId) {
        return permissionsService.findByRoleId(roleId);
    }

    @GetMapping("/find/by/roleName")
    public List<PostgresPermission> findByRoleName(@RequestParam String roleName) {
        return permissionsService.findByRoleName(roleName);
    }

    @GetMapping("/find/by/userId")
    public List<PostgresPermission> findByUserId(@RequestParam Long userId) {
        return permissionsService.findPermissionsByUserId(userId);
    }

    @GetMapping("/find/by/username")
    public List<PostgresPermission> findByUsername(@RequestParam String username) {
        return permissionsService.findPermissionsByUsername(username);
    }
}
