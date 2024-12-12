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

    public List<PostgresUser> findAll() {
        return usersRepository.findAll();
    }

    public void addUser(PostgresUser user) {
        usersRepository.addUser(user);
    }
}
