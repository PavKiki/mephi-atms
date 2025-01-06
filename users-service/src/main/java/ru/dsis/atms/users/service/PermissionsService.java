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

    public void savePermission(PostgresPermission permission) {
        permissionsRepository.save(permission);
    }

    public List<PostgresPermission> findAll() {
        return permissionsRepository.findAll();
    }

    public List<PostgresPermission> findByUserId(Long userId) {
        return permissionsRepository.findPermissionsByUserId(userId);
    }
}
