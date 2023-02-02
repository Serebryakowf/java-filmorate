package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ModelNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User createUser(User user) {
        checkName(user);
        checkLogin(user);
        user.setId(getId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        checkName(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User с данным id отсутствует");
        }
        checkLogin(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User getUserById(int id) {
        if (!users.containsKey(id)) {
            throw new ModelNotFoundException(String.format("Пользователя с id %d не существует", id));
        }
        return users.get(id);
    }

    @Override
    public User removeUserById(int id) {
        if (!users.containsKey(id)) {
            throw new ModelNotFoundException(String.format("Пользователя с id %d не существует", id));
        }
        return users.remove(id);
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
