package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {
    private final UserStorage userStorage;

    @Autowired
    public UsersService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(userStorage.getUsers().values());
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public User removeById(int id) {
        return userStorage.removeUserById(id);
    }

    public List<Integer> addFriend(int userId, int friendId) {
        User firstUser = getUserById(userId);
        User secondUser = getUserById(friendId);
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
        User firstUser = getUserById(userId);
        User secondUser = getUserById(friendId);
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
        User firstUser = getUserById(userId);
        User secondUser = getUserById(otherId);
        return firstUser.getFriends().stream()
                .filter(p -> secondUser.getFriends().contains(p))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(int userId) {
        User user = getUserById(userId);
        return user.getFriends().stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }
}
