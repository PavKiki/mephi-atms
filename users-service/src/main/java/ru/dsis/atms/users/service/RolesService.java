package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.data.RoleData;
import ru.dsis.atms.users.dao.postgres.PostgresRole;
import ru.dsis.atms.users.repository.RolesRepository;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public void saveRole(RoleData role) {
        rolesRepository.save(role);
    }

    public List<PostgresRole> findAll() {
        return rolesRepository.findAll();
    }

//    public List<PostgresRole> findRoleByUsername(String username) {
//        return rolesRepository.findRoleByUsername(username);
//    }
}