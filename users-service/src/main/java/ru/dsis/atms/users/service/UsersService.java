package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.data.UserData;
import ru.dsis.atms.users.dao.data.UserRegistrationData;
import ru.dsis.atms.users.repository.RolesRepository;
import ru.dsis.atms.users.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;

    public void saveUser(UserRegistrationData user) {
        usersRepository.save(user);
    }

    public List<UserData> findAll() {
        return usersRepository.findAll();
    }

    public UserData findById(Long id) {
        return usersRepository.findById(id);
    }

    public UserData findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public void patchUsernameByUserId(Long userId, String username) {
        usersRepository.patchUsername(userId, username);
    }

    public void patchUsernameByUsername(String username, String newUsername) {
        var user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        usersRepository.patchUsername(user.getId(), newUsername);
    }

    public void patchPasswordByUserId(Long userId, String password) {
        usersRepository.patchPassword(userId, password);
    }

    public void patchPasswordByUsername(String username, String password) {
        var user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        usersRepository.patchPassword(user.getId(), password);
    }

    public void patchNameByUserId(Long userId, String name) {
        usersRepository.patchName(userId, name);
    }

    public void patchNameByUsername(String username, String name) {
        var user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        usersRepository.patchName(user.getId(), name);
    }

    public void patchRoleByUserId(Long userId, Long roleId) {
        usersRepository.patchRole(userId, roleId);
    }

    public void patchRoleByUserId(Long userId, String roleName) {
        var role = rolesRepository.findByRoleName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        usersRepository.patchRole(userId, role.getId());
    }

    public void patchRoleByUsername(String username, Long roleId) {
        var user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        usersRepository.patchRole(user.getId(), roleId);
    }

    public void patchRoleByUsername(String username, String roleName) {
        var user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        var role = rolesRepository.findByRoleName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        usersRepository.patchRole(user.getId(), role.getId());
    }

    public void deleteById(Long id) {
        usersRepository.delete(id);
    }

    public void deleteByUsername(String username) {
        var user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        usersRepository.delete(user.getId());
    }
}