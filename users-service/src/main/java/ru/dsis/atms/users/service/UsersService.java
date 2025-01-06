package ru.dsis.atms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.users.dao.PostgresUser;
import ru.dsis.atms.users.repository.UsersRepository;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public void saveUser(PostgresUser user) {
        usersRepository.save(user);
    }

    public PostgresUser findUserById(Long id) {
        return usersRepository.findById(id);
    }

    public PostgresUser findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public void updateUser(PostgresUser user) {
        usersRepository.update(user);
    }

    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }

    public List<PostgresUser> getAllUsers() {
        return usersRepository.findAll();
    }
}