package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.postgres.PostgresPermission;
import ru.dsis.atms.users.repository.PermissionsRepository;

import java.util.List;

@Service
public class PermissionsService {
    @Autowired
    private PermissionsRepository permissionsRepository;

    public List<PostgresPermission> findAll() {
        return permissionsRepository.findAll();
    }

    public PostgresPermission findById(Long id) {
        return permissionsRepository.findById(id);
    }

    public PostgresPermission findByName(String name) {
        return permissionsRepository.findByName(name);
    }

    public List<PostgresPermission> findByRoleId(Long roleId) {
        return permissionsRepository.findByRoleId(roleId);
    }

    public List<PostgresPermission> findByRoleName(String roleName) {
        return permissionsRepository.findByRoleName(roleName);
    }

    public List<PostgresPermission> findPermissionsByUserId(Long userId) {
        return permissionsRepository.findPermissionsByUserId(userId);
    }

    public List<PostgresPermission> findPermissionsByUsername(String username) {
        return permissionsRepository.findPermissionsByUsername(username);
    }
}
