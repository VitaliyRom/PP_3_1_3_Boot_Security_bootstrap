package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getById(Long id);
    List<User> findAll();
    void save(User user);
    void deleteById(long id);
    User findByUsername(String username);
    void addUser();
    User passwordCoder(User user);

    void updateUser(Long id, User user);
}
