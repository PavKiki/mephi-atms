package ru.dsis.atms.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.users.dao.postgres.PostgresRole;
import ru.dsis.atms.users.service.RolesService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @GetMapping("/all")
    public List<PostgresRole> findAll() {
        return rolesService.findAll();
    }

    @GetMapping("/find/by/roleId")
    public PostgresRole findByRoleId(@RequestParam Long roleId) {
        return rolesService.findByRoleId(roleId);
    }

    @GetMapping("/find/by/roleName")
    public PostgresRole findByRoleName(@RequestParam String roleName) {
        return rolesService.findByRoleName(roleName);
    }

    @GetMapping("/find/by/userId")
    public PostgresRole findByUserId(@RequestParam Long userId) {
        return rolesService.findByUserId(userId);
    }

    @GetMapping("/find/by/username")
    public PostgresRole findByUsername(@RequestParam String username) {
        return rolesService.findByUsername(username);
    }
}
