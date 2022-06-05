package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private  final UserDao userDao;
    private  final RoleDao roleDao;

   @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User getById(Long id) {
        return userDao.getReferenceById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void save(User user) {
        userDao.save(passwordCoder(user));

    }

    @Override
    public void deleteById(long id) {
        userDao.deleteById(id);

    }

    @Override
    public User findByUsername(String email) {
        return userDao.findByUsername(email);
    }
    @PostConstruct
    @Override
    public void addUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleDao.findById(1L).orElse(null));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleDao.findById(1L).orElse(null));
        roles2.add(roleDao.findById(2L).orElse(null));
        User user1 = new User(1L, "John", "Smith", "user@mail.ru", 43,
                "12345", roles1);
        User user2 = new User(2L, "Peter", "Parker", "admin@mail.ru", 20,
                "54321", roles2);
        save(user1);
        save(user2);
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return user;
    }

    @Override
    public void updateUser(Long id, User user) {
       User userUp = userDao.getReferenceById(id);
       userUp.setName(user.getName());
       userUp.setSurname(user.getSurname());
       userUp.setAge(user.getAge());
       userUp.setPassword(user.getPassword());
       userUp.setRoles(user.getRoles());
       userDao.save(userUp);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

}
