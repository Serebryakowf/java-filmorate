package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UsersService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Creating User {}", user);
        return usersService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Updating User {}", user);
        return usersService.updateUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Getting all users");
        return usersService.getUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public List<Integer> addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Adding friends");
        return usersService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public List<Integer> removeFromFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Removing from friends");
        return usersService.removeFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Getting friends");
        return usersService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Getting common friends");
        return usersService.getCommonFriends(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Getting User by id");
        return usersService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public User removeById(@PathVariable Integer id) {
        log.info("Removing User by id");
        return usersService.removeById(id);
    }
}
