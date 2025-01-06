package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.PostgresPermission;
import ru.dsis.atms.users.repository.PermissionsRepository;

import java.util.List;

@Service
public class PermissionsService {
    @Autowired
    private PermissionsRepository permissionsRepository;

    public void savePermission(PostgresPermission permission) {
        permissionsRepository.save(permission);
    }

    public PostgresPermission findByName(String permissionName) {
        return permissionsRepository.findByPermissionName(permissionName);
    }

    public List<PostgresPermission> findByUserId(Long userId) {
        return permissionsRepository.findPermissionsByUserId(userId);
    }
}
