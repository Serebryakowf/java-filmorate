package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundExcetion;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(User user) {
        checkName(user);
        checkLogin(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        getById(user.getId());
        checkName(user);
        checkLogin(user);
        return userStorage.update(user);
    }

    public List<User> getList() {
        return userStorage.getList();
    }

    public User getById(int id) {
        return userStorage.getById(id).orElseThrow(() ->
                new NotFoundExcetion(String.format("Пользователя с id %d не существует", id))
        );
    }

    public User removeById(int id) {
        return userStorage.removeById(id);
    }

    public List<Integer> addFriend(int userId, int friendId) {
        User firstUser = getById(userId);
        User secondUser = getById(friendId);
        if (firstUser.getFriends().contains(friendId)) {
            throw new InternalServerException(
                    String.format("Пользователи с id %d и %d уже являются друзьями", userId, friendId)
            );
        }
        firstUser.getFriends().add(friendId);
        secondUser.getFriends().add(userId);
        return new ArrayList<>(firstUser.getFriends());
    }

    public List<Integer> removeFromFriends(int userId, int friendId) {
        User firstUser = getById(userId);
        User secondUser = getById(friendId);
        if (!firstUser.getFriends().contains(friendId)) {
            throw new InternalServerException(
                    String.format("Пользователи с id %d и %d не являются друзьями", userId, friendId)
            );
        }
        firstUser.getFriends().remove(friendId);
        secondUser.getFriends().remove(userId);
        return new ArrayList<>(firstUser.getFriends());
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        User firstUser = getById(userId);
        User secondUser = getById(otherId);
        return firstUser.getFriends().stream()
                .filter(p -> secondUser.getFriends().contains(p))
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(int userId) {
        User user = getById(userId);
        return user.getFriends().stream()
                .map(this::getById)
                .collect(Collectors.toList());
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
