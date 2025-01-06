package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.data.UserData;
import ru.dsis.atms.users.dao.postgres.PostgresUser;
import ru.dsis.atms.users.dao.data.UserDataWithPassword;
import ru.dsis.atms.users.repository.RolesRepository;
import ru.dsis.atms.users.repository.UsersRepository;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;

    public void saveUser(UserDataWithPassword user) {
        usersRepository.save(user);
    }

    public List<UserData> getAllUsers() {
        return usersRepository.findAll();
    }

    public void addRoleToUser(String username, String roleName) {
        var user = usersRepository.findByUsername(username);
        var role = rolesRepository.findByRoleName(roleName);

        usersRepository.addRoleToUser(user, role);
    }
}