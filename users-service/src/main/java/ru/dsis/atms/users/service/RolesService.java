package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.PostgresRole;
import ru.dsis.atms.users.repository.RolesRepository;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public void saveRole(PostgresRole role) {
        rolesRepository.save(role);
    }

    public PostgresRole findByRoleName(String roleName) {
        return rolesRepository.findByRoleName(roleName);
    }
}