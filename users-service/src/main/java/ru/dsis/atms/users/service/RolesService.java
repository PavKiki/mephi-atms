package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.postgres.PostgresRole;
import ru.dsis.atms.users.repository.RolesRepository;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public List<PostgresRole> findAll() {
        return rolesRepository.findAll();
    }

    public PostgresRole findByRoleId(Long id) {
        return rolesRepository.findByRoleId(id);
    }

    public PostgresRole findByRoleName(String name) {
        return rolesRepository.findByRoleName(name);
    }

    public PostgresRole findByUserId(Long userId) {
        return rolesRepository.findByUserId(userId);
    }

    public PostgresRole findByUsername(String username) {
        return rolesRepository.findByUsername(username);
    }
}