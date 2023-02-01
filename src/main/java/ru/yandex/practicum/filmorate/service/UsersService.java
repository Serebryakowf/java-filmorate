package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersService {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    public User createUser(User user) {
        checkName(user);
        checkLogin(user);
        user.setId(getId());
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        checkName(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User с данным id отсутствует");
        }
        checkLogin(user);
        users.put(user.getId(), user);
        return user;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private int getId() {
        return ++id;
    }

    private void checkLogin(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Недопустимы пробелы в login");
        }
    }

    private void checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

}
