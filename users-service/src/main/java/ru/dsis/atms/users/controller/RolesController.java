package ru.dsis.atms.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.users.dao.data.RoleData;
import ru.dsis.atms.users.dao.postgres.PostgresRole;
import ru.dsis.atms.users.service.RolesService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @PostMapping("/register")
    public void register(@RequestBody RoleData role) {
        rolesService.saveRole(role);
    }

    @GetMapping("/all")
    public List<PostgresRole> findAll() {
        return rolesService.findAll();
    }
//
//    @GetMapping("/findBy")
//    public List<PostgresRole> findRoleByUsername(@RequestParam String username) {
//        return rolesService.findRoleByUsername(username);
//    }
}
